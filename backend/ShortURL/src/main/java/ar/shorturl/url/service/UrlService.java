package ar.shorturl.url.service;

import ar.shorturl.url.service.domain.UrlBo;

public interface UrlService {
    String generateUrl(UrlBo urlBo);

    String findUrlByKey(String urlKey);
}
