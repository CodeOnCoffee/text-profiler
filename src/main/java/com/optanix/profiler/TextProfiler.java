package com.optanix.profiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TextProfiler {

    @Autowired
    public List<InsightGenerator> insightGenerators;

    public TextProfiler(List<InsightGenerator> insightGenerators) {
        this.insightGenerators = insightGenerators;
    }

    public Stats analyze(String text) {
        List<String> sentences = splitSentences(text);
        List<Insight> insights = this.insightGenerators.stream().map(insightGenerator -> insightGenerator.apply(sentences)).collect(Collectors.toList());
        return new Stats(sentences, insights);
    }

    private enum Token {START, START_OF_SENTENCE, CHAR, ESCAPE, DELIMETER, END}

    private static List<Character> DELIMITERS = Arrays.asList('.', '?', '!');

    /**
     * Split text into sentences based on delimiter of [.?!].
     * <p>
     * Supports Escaping punctuation with back-slash, e.g. \. Punctuation at start of input is treated as part of the first sentence.
     * Incomplete sentences at end of input are ignores.
     *
     * @param textInput Input to split into Sentences
     * @return List of Sentences from input
     */
    public static List<String> splitSentences(String textInput) {
        if (textInput == null || "".equals(textInput)) {
            return Collections.emptyList();
        }

        //replace line breaks with spaces
        String text = textInput.replace("\n", " ");


        // State Machine implemented as a loop
        int currentPos = 0;
        char lastChar = ' ';
        Token lastToken = Token.START;
        StringBuffer currentSentence = new StringBuffer();
        List<String> sentences = new ArrayList<>();

        do {
            char c = text.charAt(currentPos);
            switch (lastToken) {
                case ESCAPE: {
                    if (DELIMITERS.contains(c)) {
                        // escaped delimiter
                        currentSentence.append(c);
                    } else {
                        // Slash was just a slash, add it and the current
                        currentSentence.append(lastChar);
                        currentSentence.append(c);
                    }
                    lastToken = Token.CHAR;
                    break;
                }
                case DELIMETER: {
                    if (c == ' ') {
                        // Close sentence
                        currentSentence.append(lastChar);
                        sentences.add(currentSentence.toString().trim());
                        currentSentence = new StringBuffer();
                        lastToken = Token.START_OF_SENTENCE;
                    } else if (DELIMITERS.contains(c)) {
                        // two delimeters in a row, treat last one as a char
                        currentSentence.append(lastChar);
                        lastChar = c;
                        lastToken = Token.DELIMETER;
                    } else {
                        // Delimeter was in a word, not followed by a space, treat as a char. Append it and current and continue on
                        currentSentence.append(lastChar);
                        currentSentence.append(c);
                        lastToken = Token.CHAR;
                    }
                    break;
                }
                default: {
                    if (c == '\\') {
                        lastToken = Token.ESCAPE;
                        lastChar = '\\';
                    } else if (DELIMITERS.contains(c)) {
                        // potential end of sentence
                        if (lastToken == Token.START) {
                            // Delimeter at the start of the input, treat as regular char
                            System.out.println("Input started with a delimeter");
                            currentSentence.append(c);
                            lastToken = Token.CHAR;
                        } else {
                            lastChar = c;
                            lastToken = Token.DELIMETER;
                        }
                    } else {
                        // just another char
                        currentSentence.append(c);
                        lastToken = Token.CHAR;
                    }
                }
            }
        } while (++currentPos < text.length());

        // Handle last
        if (currentSentence.length() > 0) {
            if (lastToken == Token.DELIMETER) {
                currentSentence.append(lastChar);
                sentences.add(currentSentence.toString().trim());
            }
            // String ended without a punctuation mark. Buffer contains a partial sentence
            System.out.println("Partial sentence at end");
        }
        return sentences;
    }
}
