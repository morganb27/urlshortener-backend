package com.morganb27.urlshortener.controller;

import com.morganb27.urlshortener.domain.UrlMapping;
import com.morganb27.urlshortener.service.UrlShortenerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Optional;

@RestController
public class UrlRedirectionController {
    private final UrlShortenerService urlShortenerService;

    public UrlRedirectionController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirectToLongUrl(@PathVariable String code) {
        Optional<UrlMapping> urlMapping = urlShortenerService.findByShortCode(code);

        if (urlMapping.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(urlMapping.get().getLongUrl()));
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }
        return ResponseEntity.notFound().build();
    }
}
