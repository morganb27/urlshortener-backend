package com.morganb27.urlshortener.controller;

import com.morganb27.urlshortener.dto.UrlRequestDto;
import com.morganb27.urlshortener.dto.UrlResponseDto;
import com.morganb27.urlshortener.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/url")
public class UrlShortenerController {
    private final UrlShortenerService urlShortenerService;

    @Autowired
    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<UrlResponseDto> shortenUrl(@RequestBody UrlRequestDto urlRequestDto) throws NoSuchAlgorithmException {
        UrlResponseDto urlResponseDto = urlShortenerService.createShortUrl(urlRequestDto.getLongUrl());
        return ResponseEntity.ok(urlResponseDto);
    }

    @DeleteMapping("/delete/{code}")
    public ResponseEntity<Void> deleteShortUrl(@PathVariable String code) {
        boolean isDeleted = urlShortenerService.deleteShortUrl(code);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
