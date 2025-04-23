package kz.ssss.rate_limiter.service;

import io.github.bucket4j.*;

import java.time.Duration;

public enum PricingPlan {
    FREE {
        @Override
        Bandwidth getLimit() {
            return Bandwidth.classic(20, Refill.greedy(20, Duration.ofHours(1)));
        }
    },
    BASIC {
        @Override
        Bandwidth getLimit() {
            return Bandwidth.classic(40, Refill.greedy(40, Duration.ofHours(1)));
        }
    },
    PROFESSIONAL {
        @Override
        Bandwidth getLimit() {
            return Bandwidth.classic(100, Refill.greedy(100, Duration.ofHours(1)));
        }
    };

    abstract Bandwidth getLimit();

    static PricingPlan resolvePlanFromApiKey(String apiKey) {
        if (apiKey == null || apiKey.isEmpty()) {
            return FREE;
        } else if (apiKey.startsWith("PX001-")) {
            return PROFESSIONAL;
        } else if (apiKey.startsWith("BX001-")) {
            return BASIC;
        }
        return FREE;
    }
}

