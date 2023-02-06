package ru.practicum.ewm.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ViewStatisticsDto {
    private String app;
    private String uri;
    private Long hits;
}
