package ru.practicum.ewm.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewm.dto.HitCriteria;

import java.util.List;
import java.util.Map;

@Component
public class StatsClient {
    private static final String URL = "http://localhost:9090";
    protected final RestTemplate rest;

    public StatsClient(RestTemplateBuilder builder) {
        this.rest = builder.build();
    }


    public ResponseEntity<Object> post(EndpointHitDto endpointHitDto) {
        return makeAndSendRequest(HttpMethod.POST, URL + "/hit", null, endpointHitDto);
    }

    public ResponseEntity<Object> get(HitCriteria hitCriteria) {
        return makeAndSendRequest(HttpMethod.GET, URL + "/stats", null, hitCriteria);
    }

    private <T> ResponseEntity<Object> makeAndSendRequest(HttpMethod method, String path,
                                                          @Nullable Map<String, Object> parameters, @Nullable T body) {
        HttpEntity<T> requestEntity = new HttpEntity<>(body, defaultHeaders());

        ResponseEntity<Object> ewmStatServiceResponse;
        try {
            if (parameters != null) {
                ewmStatServiceResponse = rest.exchange(path, method, requestEntity, Object.class, parameters);
            } else {
                ewmStatServiceResponse = rest.exchange(path, method, requestEntity, Object.class);
            }
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
        return prepareGatewayResponse(ewmStatServiceResponse);
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        return headers;
    }

    private static ResponseEntity<Object> prepareGatewayResponse(ResponseEntity<Object> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }

        return responseBuilder.build();
    }
}
