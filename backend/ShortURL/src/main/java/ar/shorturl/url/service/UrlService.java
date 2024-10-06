package ar.shorturl.url.service;

import ar.shorturl.url.repository.entity.Url;
import ar.shorturl.url.service.domain.UrlBo;

public interface UrlService {
    String generateUrl(UrlBo urlBo);

    Url findUrlByKey(String urlKey, Boolean plusClick);
}
