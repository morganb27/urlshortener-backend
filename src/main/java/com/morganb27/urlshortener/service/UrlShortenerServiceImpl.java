package com.morganb27.urlshortener.service;

import com.morganb27.urlshortener.domain.UrlMapping;
import com.morganb27.urlshortener.dto.UrlResponseDto;
import com.morganb27.urlshortener.repository.UrlMappingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Optional;

@Service
@Transactional
public class UrlShortenerServiceImpl implements UrlShortenerService {
    private final UrlMappingRepository urlMappingRepository;

    @Autowired
    public UrlShortenerServiceImpl(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }

    @Override
    public UrlResponseDto createShortUrl(String longUrl) throws NoSuchAlgorithmException {

        Optional<UrlMapping> urlMappingOptional = urlMappingRepository.findByLongUrl(longUrl);

        if (urlMappingOptional.isPresent()) {
            UrlMapping existingUrlMapping = urlMappingOptional.get();
            return new UrlResponseDto(existingUrlMapping.getLongUrl(), existingUrlMapping.getShortUrl(), existingUrlMapping.getCode());
        }

        String code = generateShortCode(longUrl);
        String shortUrl = "http://localhost:8080/" + code;
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setLongUrl(longUrl);
        urlMapping.setShortUrl(shortUrl);
        urlMapping.setCode(code);
        urlMapping.setCreatedOnUtc(Instant.now());
        urlMappingRepository.save(urlMapping);

        return new UrlResponseDto(longUrl, shortUrl, code);
    }

    @Override
    public Optional<UrlMapping> findByShortCode(String code) {
        return urlMappingRepository.findByCode(code);
    }

    @Override
    public boolean deleteShortUrl(String code) {
        Optional<UrlMapping> urlMapping = urlMappingRepository.findByCode(code);
        if (urlMapping.isPresent()) {
            urlMappingRepository.delete(urlMapping.get());
            return true;
        }
        return false;
    }


    private String generateShortCode(String longUrl) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = md.digest(longUrl.getBytes());
        return bytesToHex(hashBytes).substring(0, 8);

    }

    private String bytesToHex(byte[] hashBytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b: hashBytes) {
            hexString.append(String.format("%02x", b & 0xFF));
        }
        return hexString.toString();
    }


}
