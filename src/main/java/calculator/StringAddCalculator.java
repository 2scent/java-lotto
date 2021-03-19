package calculator;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringAddCalculator {
    private static final int DEFAULT_RESULT = 0;

    public static int splitAndSum(final String text) {
        if (nullOrEmpty(text)) {
            return DEFAULT_RESULT;
        }

        final String[] tokens = tokens(text);

        if (containsNegative(tokens)) {
            throw new RuntimeException();
        }

        return sum(tokens);
    }

    private static boolean nullOrEmpty(final String text) {
        return text == null || text.isEmpty();
    }

    private static String[] tokens(final String text) {
        final Matcher m = Pattern.compile("//(.)\n(.*)").matcher(text);
        if (m.find()) {
            final String customDelimiter = m.group(1);
            return m.group(2).split(customDelimiter);
        }

        return text.split(",|:");
    }

    private static boolean containsNegative(final String[] tokens) {
        return Arrays.stream(tokens).mapToInt(Integer::parseInt).anyMatch(StringAddCalculator::negative);
    }

    private static boolean negative(final int n) {
        return n < 0;
    }

    private static int sum(final String[] tokens) {
        return Arrays.stream(tokens).mapToInt(Integer::parseInt).sum();
    }
}
