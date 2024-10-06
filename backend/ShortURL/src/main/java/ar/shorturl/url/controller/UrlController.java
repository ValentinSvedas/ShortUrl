package ar.shorturl.url.controller;

import ar.shorturl.url.controller.dto.UrlRequestDto;
import ar.shorturl.url.controller.dto.UrlResponseDto;
import ar.shorturl.url.repository.entity.Url;
import ar.shorturl.url.service.UrlService;
import ar.shorturl.url.service.domain.UrlBo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class UrlController {

    private final UrlService urlService;

    @PostMapping
    public @ResponseBody
    ResponseEntity<String> run(
            @RequestBody UrlRequestDto urlRequestDto
    ){
        return ResponseEntity.ok(urlService.generateUrl(new UrlBo(urlRequestDto)));
    }

    @GetMapping("/{id}")
    public void method(HttpServletResponse httpServletResponse, @PathVariable("id") String urlKey) {
        httpServletResponse.setHeader("Location", urlService.findUrlByKey(urlKey, true).getOriginalUrl());
        httpServletResponse.setStatus(302);
    }

    @GetMapping("url/{id}")
    public UrlResponseDto method(
            @PathVariable("id") String urlKey
    ) {
        Url urlByKey = urlService.findUrlByKey(urlKey, false);

        return UrlResponseDto.builder()
                .originalUrl(urlByKey.getOriginalUrl())
                .expiresAt(urlByKey.getExpiresAt())
                .clicks(urlByKey.getClicks())
                .build();
    }


}
