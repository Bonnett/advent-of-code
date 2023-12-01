package uk.co.pete_b.advent.aoc2023;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;

public class Day01 {
    public static int getCalibrationSum(final List<String> calibrationLines) {
        int sum = 0;
        for (String line : calibrationLines) {
            char firstDigit = '0';
            for (char c : line.toCharArray()) {
                if (c >= 48 && c <= 57) {
                    firstDigit = c;
                    break;
                }
            }

            char lastDigit = '0';
            final char[] charArray = line.toCharArray();
            ArrayUtils.reverse(charArray);
            for (char c : charArray) {
                if (c >= 48 && c <= 57) {
                    lastDigit = c;
                    break;
                }
            }
            sum += Integer.parseInt((firstDigit + "" + lastDigit));
        }

        return sum;
    }
    public static int getCalibrationSumFromWords(final List<String> calibrationLines) {
        int sum = 0;
        final List<String> words = Arrays.asList("one", "two", "three", "four", "five", "six", "seven", "eight", "nine");
        for (String line : calibrationLines) {
            int maxIndex = -1;
            int minIndex = line.length();
            int firstDigit = 0;
            int lastDigit = 1;

            for (int i=0; i<words.size(); i++){
                final String word = words.get(i);
                final int indexOf = line.indexOf(word);
                final int lastIndexOf = line.lastIndexOf(word);
                if (indexOf != -1)
                {
                    if (lastIndexOf > maxIndex)
                    {
                        maxIndex = lastIndexOf;
                        lastDigit = i + 1;
                    }

                    if (indexOf < minIndex)
                    {
                        minIndex = indexOf;
                        firstDigit = i + 1;
                    }
                }
            }

            final char[] charArray = line.toCharArray();
            for (int i=0; i<charArray.length && i<minIndex; i++) {
                final char c = charArray[i];
                if (c >= 48 && c <= 57) {
                    firstDigit = Integer.parseInt("" + c);
                    break;
                }
            }

            for (int i=charArray.length - 1; i>maxIndex; i--) {
                final char c = charArray[i];
                if (c >= 48 && c <= 57) {
                    lastDigit = Integer.parseInt("" + c);
                    break;
                }
            }

            sum += Integer.parseInt((firstDigit + "" + lastDigit));
        }

        return sum;
    }
}
