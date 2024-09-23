package ar.shorturl.url.repository;

import ar.shorturl.url.repository.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, String> {
}
