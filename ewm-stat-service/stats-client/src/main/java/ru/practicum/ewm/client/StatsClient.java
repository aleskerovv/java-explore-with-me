package ru.practicum.ewm.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewm.dto.EndpointHitResponseDto;
import ru.practicum.ewm.dto.HitCriteria;
import ru.practicum.ewm.dto.ViewStatisticsDto;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class StatsClient {
    protected final WebClient client;
    protected static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public StatsClient(WebClient client) {
        this.client = client;
    }

    public EndpointHitResponseDto post(EndpointHitDto endpointHitDto) {
        return client.post()
                .uri("/hit")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(endpointHitDto), EndpointHitDto.class)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<EndpointHitResponseDto>() {
                })
                .block();

    }

    public List<ViewStatisticsDto> get(HitCriteria hitCriteria) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/stats")
                        .queryParam("uris", hitCriteria.getUris())
                        .queryParam("start", hitCriteria.getStart().format(FORMATTER))
                        .queryParam("end", hitCriteria.getEnd().format(FORMATTER))
                        .queryParam("unique", hitCriteria.isUnique())
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ViewStatisticsDto>>() {})
                .block();
    }
}