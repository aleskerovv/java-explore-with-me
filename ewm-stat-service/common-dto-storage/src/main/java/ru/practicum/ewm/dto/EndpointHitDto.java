package ru.practicum.ewm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Value
@Builder
@Jacksonized
public class EndpointHitDto {
    @NotNull(message = "'app' can not be null")
    String app;
    @NotNull(message = "'uri' can not be null")
    String uri;
    @NotNull(message = "'ip' can not be null")
    String ip;
    @NotNull(message = "'timestamp' can not be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime timestamp;
}