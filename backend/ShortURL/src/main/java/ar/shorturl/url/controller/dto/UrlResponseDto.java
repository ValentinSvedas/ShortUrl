package ar.shorturl.url.controller.dto;

import ar.shorturl.url.repository.entity.Url;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UrlResponseDto (
        String originalUrl,
        LocalDateTime expiresAt,
        Integer clicks
){ }