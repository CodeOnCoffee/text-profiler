package com.optanix.profiler;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import com.optanix.profiler.insights.TotalLetterCountInsight;
import com.optanix.profiler.insights.TotalLetterCountInsightGenerator;
import com.optanix.profiler.insights.WordsPerSentenceInsight;
import com.optanix.profiler.insights.WordsPerSentenceInsightGenerator;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TextProfilerTest {


    private void test(String input, String[] expected) {
        List<String> strings = TextProfiler.splitSentences(input);
        assertArrayEquals(expected, strings.toArray());
    }

    @Test
    public void ignoreDelimeterAtStart() {
        test("?test at start. Second sentence.", new String[] {"?test at start.", "Second sentence."});
    }

    @Test
    public void ignoreFragmentSentenceAtEnd() {
        test("First sentence! Second sentence fragment", new String[] {"First sentence!"});
    }

    @Test
    public void ignoreDelimetersInWords() {
        test("First.sentence! Second sentence?", new String[] {"First.sentence!", "Second sentence?"});
    }

    @Test
    public void supportEscaping() {
        test("First\\. sentence! Second sentence?", new String[] {"First. sentence!", "Second sentence?"});
    }

    @Test
    public void testTotalLetterCountInsight() {
        TotalLetterCountInsightGenerator generator = new TotalLetterCountInsightGenerator();
        TotalLetterCountInsight insight = generator.apply(Arrays.asList("AabBbcccc", "abc"));
        assertEquals(insight.countPerLetter.get('a').intValue(), 3);
        assertEquals(insight.countPerLetter.get('b').intValue(), 4);
        assertEquals(insight.countPerLetter.get('c').intValue(), 5);
    }

    @Test
    public void testWorldsPerSentenceInsight() {
        WordsPerSentenceInsightGenerator generator = new WordsPerSentenceInsightGenerator();
        String sen1 = "The quick brown fox jumps over the lazy dog";
        String sen2 = "The Rain stays mainly in the plain";
        WordsPerSentenceInsight insight = generator.apply(Arrays.asList(sen1, sen2));
        assertEquals(insight.wordsPerSentence.get(sen1).intValue(), 9);
        assertEquals(insight.wordsPerSentence.get(sen2).intValue(), 7);

    }


}