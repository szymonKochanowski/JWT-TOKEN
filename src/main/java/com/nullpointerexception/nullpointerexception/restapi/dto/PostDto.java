package com.nullpointerexception.nullpointerexception.restapi.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostDto {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime created;
}
