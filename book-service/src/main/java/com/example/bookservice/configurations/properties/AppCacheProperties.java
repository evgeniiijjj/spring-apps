package com.example.bookservice.configurations.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.cache")
public class AppCacheProperties {
    private final List<String> cacheNames = new ArrayList<>();
    private final Map<String, CacheProperties> caches = new HashMap<>();
    private CacheType cacheType;

    @Getter
    @Setter
    public static class CacheProperties {
        private Duration expire = Duration.ZERO;
    }

    public interface CacheNames {
        String BOOKS = "books";
        String BOOK = "book";
    }

    public enum CacheType {
        IN_MEMORY,
        REDIS
    }
}
