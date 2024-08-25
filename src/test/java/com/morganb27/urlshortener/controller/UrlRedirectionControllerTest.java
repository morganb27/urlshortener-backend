package com.morganb27.urlshortener.controller;

import com.morganb27.urlshortener.domain.UrlMapping;
import com.morganb27.urlshortener.service.UrlShortenerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UrlRedirectionController.class)
@ExtendWith(SpringExtension.class)
public class UrlRedirectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlShortenerService urlShortenerService;

    @Test
    public void testUrlRedirectionSuccess() throws Exception {
        String code = "12345678";
        String longUrl = "http://example.com";
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setLongUrl(longUrl);

        when(urlShortenerService.findByShortCode(code)).thenReturn(Optional.of(urlMapping));

        mockMvc.perform(get("/{code}", code))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", longUrl));
    }

    @Test
    public void testUrlToLongurlNotFound() throws Exception {
        String code = "12345678";

        when(urlShortenerService.findByShortCode(code)).thenReturn((Optional.empty()));

        mockMvc.perform(get("/{code}", code))
                .andExpect(status().isNotFound());
    }
}
