package com.optanix.profiler;

import java.util.List;

/**
 * Return Entity from Profiler.
 */
public class Stats {

    private List<Insight> insights;
    private List<String> sentences;

    public Stats(List<String> sentences, List<Insight> insights) {
        this.sentences = sentences;
        this.insights = insights;
    }

    public Stats() {

    }

    public List<Insight> getInsights() {
        return insights;
    }

    public void setInsights(List<Insight> insights) {
        this.insights = insights;
    }

    public List<String> getSentences() {
        return sentences;
    }

    public void setSentences(List<String> sentences) {
        this.sentences = sentences;
    }
}
