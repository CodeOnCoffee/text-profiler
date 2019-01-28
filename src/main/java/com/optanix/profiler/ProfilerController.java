package com.optanix.profiler;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfilerController {

    private TextProfiler profiler;

    public ProfilerController(TextProfiler profiler) {
        this.profiler = profiler;
    }

    @PostMapping("api/analyze")
    public Stats analyzeText(@RequestBody String text) {
        Stats stats = profiler.analyze(text);
        return stats;
    }

}
