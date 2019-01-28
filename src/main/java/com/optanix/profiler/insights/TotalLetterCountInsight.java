package com.optanix.profiler.insights;

import com.optanix.profiler.Insight;

import java.util.Map;

public class TotalLetterCountInsight implements Insight {
    public Map<Character, Integer> countPerLetter;

    public TotalLetterCountInsight(Map<Character, Integer> countPerLetter) {
        this.countPerLetter = countPerLetter;
    }
}
