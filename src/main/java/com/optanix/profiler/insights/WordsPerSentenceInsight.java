package com.optanix.profiler.insights;

import com.optanix.profiler.Insight;

import java.util.Map;

public class WordsPerSentenceInsight implements Insight {

    public Map<String, Integer> wordsPerSentence;

    public WordsPerSentenceInsight(Map<String, Integer> wordsPerSentence) {
        this.wordsPerSentence = wordsPerSentence;
    }
}
