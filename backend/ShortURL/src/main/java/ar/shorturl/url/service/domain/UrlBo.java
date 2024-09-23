package ar.shorturl.url.service.domain;

import ar.shorturl.url.controller.dto.UrlRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UrlBo {
    String originalUrl;
    String shortedUrl;
    LocalDateTime expiresAt;

    public UrlBo(UrlRequestDto urlRequestDto) {
        this.originalUrl = urlRequestDto.url();
    }
}
