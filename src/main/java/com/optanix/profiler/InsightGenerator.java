package com.optanix.profiler;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Given a list of sentences generate some Insight
 */
public interface InsightGenerator extends Function<List<String>, Insight> {
    List<Character> WHITESPACE = Arrays.asList('\n', ' ');
}
