package ar.shorturl.url.controller;

import ar.shorturl.url.controller.dto.UrlRequestDto;
import ar.shorturl.url.service.UrlService;
import ar.shorturl.url.service.domain.UrlBo;
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
    public ResponseEntity<String> one(@PathVariable("id") String urlKey) {
        log.info("Looking for url key -> {}", urlKey);
        return ResponseEntity.ok(urlService.findUrlByKey(urlKey));
    }

}
