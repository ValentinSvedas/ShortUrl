package ar.shorturl.url.service.impl;

import ar.shorturl.url.exception.NotFoundException;
import ar.shorturl.url.exception.OperationNotValidException;
import ar.shorturl.url.repository.UrlRepository;
import ar.shorturl.url.repository.entity.Url;
import ar.shorturl.url.service.UrlService;
import ar.shorturl.url.service.domain.UrlBo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    @Value("${shorturl.url.base}")
    private String urlBase;

    @Value("${expiration.time}")
    private Integer daysToExpires;

    private final UrlRepository urlRepository;
    private static final String ADDITIONAL_CHARS = "/";
    private static final int ID_LENGTH = 8;

    private static final String URL_REGEX = "^https?://(www\\.)?[a-z0-9]+([.-][a-z0-9]+)*\\.[a-z]{2,5}(:\\d{1,5})?(/.*)?$";
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX, Pattern.CASE_INSENSITIVE);



    @Override
    public String generateUrl(UrlBo urlBo){
        log.info("Generating short URL for original URL: {}", urlBo.getOriginalUrl());
        validateUrl(urlBo);
        String randomId = generateRandomId();
        String shortedUrl = urlBase + ADDITIONAL_CHARS + randomId;
        LocalDateTime calculateDaysToExpires = LocalDateTime.now().plusDays(daysToExpires);

        Url url = new Url(randomId, urlBo.getOriginalUrl(), shortedUrl,
                LocalDateTime.now(), calculateDaysToExpires);
        return urlRepository.save(url).getShortedUrl();
    }

    private void validateUrl(UrlBo urlBo) {
        String originalUrl = urlBo.getOriginalUrl();
        if (Objects.isNull(originalUrl) || originalUrl.isEmpty())
            throw new OperationNotValidException("invalid-url", "La URL original no puede ser nula.");
        if (!isValidUrl(originalUrl))
            throw new OperationNotValidException("invalid-url", "La URL no presenta un protocolo HTTPS o tiene un formato incorrecto. Intente nuevamente.");
    }

    private String generateRandomId() {
        return RandomStringUtils.random(ID_LENGTH, true, true);
    }

    @Override
    public String findUrlByKey(String urlKey) {
       Url url = urlRepository.findById(urlKey)
               .orElseThrow(() -> new NotFoundException("url-not-found", "No se encontró la url original con ID: {}.", urlKey));
        return urlRepository.save(url).getOriginalUrl();
    }


    public boolean isValidUrl(String url) {
        return URL_PATTERN.matcher(url).matches();
    }
}
