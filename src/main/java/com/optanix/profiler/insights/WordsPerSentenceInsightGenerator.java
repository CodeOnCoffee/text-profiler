package com.optanix.profiler.insights;

import com.optanix.profiler.InsightGenerator;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class WordsPerSentenceInsightGenerator implements InsightGenerator {

    @Override
    public WordsPerSentenceInsight apply(List<String> strings) {
        return new WordsPerSentenceInsight(strings.stream().collect(Collectors.toMap(Function.identity(), this::countWords)));
    }

    private int countWords(String sentence) {
        return sentence.split("\\s").length;
    }

}
