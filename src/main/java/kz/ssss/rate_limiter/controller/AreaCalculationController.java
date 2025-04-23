package kz.ssss.rate_limiter.controller;

import kz.ssss.rate_limiter.model.AreaV1;
import kz.ssss.rate_limiter.model.RectangleDimensionsV1;
import kz.ssss.rate_limiter.model.TriangleDimensionsV1;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/area")
public class AreaCalculationController {

    @PostMapping("/rectangle")
    public ResponseEntity<AreaV1> rectangle(
            @RequestBody RectangleDimensionsV1 dims) {
        return ResponseEntity.ok(
                new AreaV1("rectangle", dims.length() * dims.width()));
    }

    @PostMapping("/triangle")
    public ResponseEntity<AreaV1> triangle(
            @RequestBody TriangleDimensionsV1 dims) {
        return ResponseEntity.ok(
                new AreaV1("triangle", 0.5 * dims.base() * dims.height()));
    }
}
