package kz.iiitu.spring_lab_01.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HelloController {

    @Value("${app.owner:unknown}")
    private String owner;

    @GetMapping("/hello")
    public Greeting hello(@RequestParam(defaultValue = "world") String name) {
        return new Greeting("Hello, " + name + "!", owner, LocalDateTime.now());
    }

    @GetMapping("/info")
    public Info info() {
        return new Info(owner,
                System.getProperty("java.version"),
                Runtime.getRuntime().availableProcessors());
    }

    // === ИНДИВИДУАЛЬНОЕ ЗАДАНИЕ (вариант 6) ===
    @GetMapping("/stats")
    public Map<String, Object> stats(
            @RequestParam(defaultValue = "0") String numbers) {

        double[] values = Arrays.stream(numbers.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .mapToDouble(Double::parseDouble)
                .toArray();

        if (values.length == 0) {
            return Map.of(
                    "error", "empty input",
                    "min", 0, "max", 0, "average", 0, "count", 0
            );
        }

        double min = Arrays.stream(values).min().orElse(0);
        double max = Arrays.stream(values).max().orElse(0);
        double average = Arrays.stream(values).average().orElse(0);

        return Map.of(
                "min", min,
                "max", max,
                "average", average,
                "count", values.length
        );
    }

    public record Greeting(String message, String owner, LocalDateTime timestamp) {}
    public record Info(String owner, String javaVersion, int cpuCores) {}
}