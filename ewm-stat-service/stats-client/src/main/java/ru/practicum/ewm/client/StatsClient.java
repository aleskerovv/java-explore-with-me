package ru.practicum.ewm.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewm.dto.EndpointHitResponseDto;

public class StatsClient {
    protected final WebClient client;

    public StatsClient(WebClient client) {
        this.client = client;
    }

    public EndpointHitResponseDto post(EndpointHitDto endpointHitDto) {
        return client.post()
                .uri("/hit")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(endpointHitDto), EndpointHitDto.class)
                .retrieve()
                .bodyToMono(EndpointHitResponseDto.class)
                .block();
    }
}
