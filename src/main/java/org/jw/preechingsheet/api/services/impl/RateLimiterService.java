package org.jw.preechingsheet.api.services.impl;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jw.preechingsheet.api.services.IRateLimiterService;
import org.springframework.stereotype.Service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RateLimiterService implements IRateLimiterService {
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Override
    public boolean isRateLimited(String ip) {
    	log.info("Trying to consume 1 token from client ip {}", ip);
    	Bucket bucket = buckets.computeIfAbsent(ip, k -> createNewBucket());
        return bucket.tryConsume(1);
    }

    private Bucket createNewBucket() {
        Bandwidth limit = Bandwidth.classic(10, Refill.intervally(10, Duration.ofSeconds(30)));
        return Bucket.builder().addLimit(limit).build();
    }
}