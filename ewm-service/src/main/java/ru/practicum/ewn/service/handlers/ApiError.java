package ru.practicum.ewn.service.handlers;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
@Value
@Jacksonized
public class ApiError {
    HttpStatus status;
    String reason;
    String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime timestamp = LocalDateTime.now();
}
