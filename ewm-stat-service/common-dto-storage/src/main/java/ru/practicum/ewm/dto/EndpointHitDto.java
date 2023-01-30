package ru.practicum.ewm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class EndpointHitDto {
    @NotNull(message = "'app' can not be null")
    private String app;
    @NotNull(message = "'uri' can not be null")
    private String uri;
    @NotNull(message = "'ip' can not be null")
    private String ip;
    @NotNull(message = "'timestamp' can not be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}
