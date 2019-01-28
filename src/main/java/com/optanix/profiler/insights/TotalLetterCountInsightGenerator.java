package com.optanix.profiler.insights;

import com.optanix.profiler.InsightGenerator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

@Component
public class TotalLetterCountInsightGenerator implements InsightGenerator {

    public TotalLetterCountInsight apply(List<String> strings) {
        Map<Character, AtomicInteger> countPerLetter = new HashMap<>();

        strings.stream().map(String::toLowerCase).flatMap(s -> s.chars().boxed())
                .filter(c -> !WHITESPACE.contains((char) c.intValue()))      // don't count whitespace
                .forEach(
                        c -> countPerLetter.computeIfAbsent((char) c.intValue(), (x) -> new AtomicInteger(0)).getAndIncrement()
                );

        LinkedHashMap<Character, Integer> sortedMap = new LinkedHashMap<>();

        // sort by value and populate map
        countPerLetter.entrySet().stream().sorted(Comparator.comparingInt( e -> e.getValue().intValue()))
                .forEach( e -> sortedMap.put(e.getKey(), e.getValue().intValue()));


        return new TotalLetterCountInsight(sortedMap);
    }
}