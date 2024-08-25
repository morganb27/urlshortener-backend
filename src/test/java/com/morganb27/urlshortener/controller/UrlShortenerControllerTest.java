package com.morganb27.urlshortener.controller;

import com.morganb27.urlshortener.dto.UrlRequestDto;
import com.morganb27.urlshortener.dto.UrlResponseDto;
import com.morganb27.urlshortener.service.UrlShortenerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UrlShortenerController.class)
@ExtendWith(SpringExtension.class)
public class UrlShortenerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlShortenerService urlShortenerService;

    @Test
    public void testShortenUrlSuccess() throws Exception {
        UrlRequestDto requestDto = new UrlRequestDto("http://example.com");
        UrlResponseDto responseDto = new UrlResponseDto("http://example.com", "http://short.url/abc123", "abc123");

        when(urlShortenerService.createShortUrl(anyString())).thenReturn(responseDto);

        mockMvc.perform(post("/api/url/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"longUrl\":\"http://example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortUrl").value("http://short.url/abc123"));
    }

    @Test
    public void testDeleteShortUrlSuccess() throws Exception {
        String code = "12345678";
        when(urlShortenerService.deleteShortUrl(code)).thenReturn(true);

        mockMvc.perform(delete("/api/url/delete/{code}", code))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteShortUrlNotFound() throws Exception {
        String code = "12345678";
        when(urlShortenerService.deleteShortUrl(code)).thenReturn(false);

        mockMvc.perform(delete("/api/url/delete/{code}", code))
                .andExpect(status().isNotFound());
    }
}
