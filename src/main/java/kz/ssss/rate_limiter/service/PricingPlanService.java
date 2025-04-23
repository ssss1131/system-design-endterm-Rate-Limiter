package kz.ssss.rate_limiter.service;

import io.github.bucket4j.Bucket;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class PricingPlanService {

    private final ConcurrentHashMap<String, Bucket> cache = new ConcurrentHashMap<>();

    public Bucket resolveBucket(String apiKey) {
        return cache.computeIfAbsent(apiKey == null ? "" : apiKey, this::newBucket);
    }

    private Bucket newBucket(String key) {
        PricingPlan plan = PricingPlan.resolvePlanFromApiKey(key);
        return Bucket.builder()
                .addLimit(plan.getLimit())
                .build();
    }
}
