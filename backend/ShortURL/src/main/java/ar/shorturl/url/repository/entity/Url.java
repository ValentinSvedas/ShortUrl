package ar.shorturl.url.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "url")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Url {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "id", length = 8)
    private String id;

    @Column(nullable = false, name = "original_url")
    private String originalUrl;

    @Column(nullable = false, name = "shorted_url")
    private String shortedUrl;

    @Column(nullable = false, name = "update_at")
    private LocalDateTime updatedAt;

    @Column(nullable = false, name = "expires_at")
    private LocalDateTime expiresAt;

}
