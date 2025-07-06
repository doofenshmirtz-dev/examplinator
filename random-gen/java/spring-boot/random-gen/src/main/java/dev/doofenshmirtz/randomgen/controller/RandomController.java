package dev.doofenshmirtz.randomgen.controller;

import dev.doofenshmirtz.randomgen.model.RandomRequest;
import dev.doofenshmirtz.randomgen.service.RandomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/getRandom")
public class RandomController {

    private final RandomService randomService;

    public RandomController(RandomService randomService) {
        this.randomService = randomService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Long>> getRandom(@RequestParam(required = false) String min,
                                                       @RequestParam(required = false) String max) {
        return ResponseEntity.ok(Map.of("random", randomService.getRandom(min, max)));
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> postRandom(@RequestBody RandomRequest request) {
        return ResponseEntity.ok(Map.of("random", randomService.getRandom(request.getMin(), request.getMax())));
    }
}
