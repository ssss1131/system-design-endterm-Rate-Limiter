# Overview

High-volume traffic spikes can degrade the performance of your API and lead to outages. This project implements a Token Bucket rate-limiter using the Bucket4j library to:

Throttle client requests based on a configurable limit (e.g., 20 requests per minute).

Reject excess requests with HTTP 429 (Too Many Requests).

Expose response headers:

X-Rate-Limit-Remaining: tokens left in current window.

X-Rate-Limit-Retry-After-Seconds: wait time until next token.

The solution uses an in-memory bucket per client key (API Key or IP) and is easily replaceable with a distributed store.

# Algorithm & Library

Token Bucket Algorithm:

A bucket holds up to N tokens (capacity).

Each incoming request consumes one token.

Tokens are refilled at a fixed rate (e.g., N tokens every minute).

If the bucket is empty, requests are rejected until new tokens arrive.

Library: Bucket4j is a thread-safe Java implementation of Token Bucket, widely used for API rate-limiting.

# How It Works

Filter: a custom OncePerRequestFilter intercepts all /api/** calls.

Bucket: per-client Bucket instance created via Bucket4j, configured from application.yml.

Consume: each request calls bucket.tryConsumeAndReturnRemaining(1).

Headers: if consumed, response includes X-Rate-Limit-Remaining; if not, HTTP 429 with X-Rate-Limit-Retry-After-Seconds.

# Project launch
1. clone the repo
```
git clone https://github.com/ssss1131/system-design-endterm-Rate-Limiter.git
```

2. enter the folder
```
cd system-design-endterm-Rate-Limiter
```

3. install libraries and start project
```
./mvnw clean spring-boot:run
```

4.request 21 times to see rate limit
```
for i in {1..22}; do 
  echo "Request #$i:" \
    && curl -i -X POST http://localhost:8080/api/v1/area/rectangle \
      -H "Content-Type: application/json" \
      -H "X-api-key: USER123" \
      -d '{ "length": 5, "width": 4 }'
  echo

done
```
