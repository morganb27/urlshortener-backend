package com.morganb27.urlshortener.service;

import com.morganb27.urlshortener.domain.UrlMapping;
import com.morganb27.urlshortener.dto.UrlResponseDto;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public interface UrlShortenerService {
    UrlResponseDto createShortUrl(String longUrl) throws NoSuchAlgorithmException;

    Optional<UrlMapping> findByShortCode(String code);

    boolean deleteShortUrl(String code);

}
