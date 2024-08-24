package com.morganb27.urlshortener.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Setter
@Getter
public class UrlMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="long_url", nullable = false)
    private String longUrl;

    @Column(name="short_url", nullable = false)
    private String shortUrl;

    @Column(name="code", nullable = false, unique = true)
    private String code;

    @Column(name="created_on_utc", nullable = false)
    private Instant createdOnUtc;

}
