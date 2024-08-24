package com.morganb27.urlshortener.repository;

import com.morganb27.urlshortener.domain.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
    Optional<UrlMapping> findByCode(String code);
    Optional<UrlMapping> findByLongUrl(String longUrl);
}
