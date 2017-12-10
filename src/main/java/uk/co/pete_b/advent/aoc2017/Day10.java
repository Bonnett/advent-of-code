package uk.co.pete_b.advent.aoc2017;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

public class Day10 {

    public static int calculateHashNonAscii(final int stringLength, final String input) {
        final Integer[] lengths = Arrays.stream(input.split(",")).map(String::trim).map(Integer::parseInt).toArray(Integer[]::new);
        int[] inputList = new int[stringLength];
        for (int i = 0; i < inputList.length; i++) {
            inputList[i] = i;
        }

        int currentPosition = 0;
        int skipSize = 0;

        for (Integer length : lengths) {
            int[] toReverse = new int[length];
            for (int i = 0; i < toReverse.length; i++) {
                int index = (currentPosition + i) % inputList.length;
                toReverse[i] = inputList[index];
            }
            ArrayUtils.reverse(toReverse);
            for (int i = 0; i < toReverse.length; i++) {
                int index = (currentPosition + i) % inputList.length;
                inputList[index] = toReverse[i];
            }

            currentPosition += length + skipSize++;
        }

        return inputList[0] * inputList[1];
    }

    public static String calculateHashAscii(final String input) {
        final int[] lengths = new int[input.length() + 5];
        lengths[lengths.length - 5] = 17;
        lengths[lengths.length - 4] = 31;
        lengths[lengths.length - 3] = 73;
        lengths[lengths.length - 2] = 47;
        lengths[lengths.length - 1] = 23;

        for (int i = 0; i < input.length(); i++) {
            lengths[i] = (int) input.charAt(i);
        }
        int[] inputList = new int[256];
        for (int i = 0; i < inputList.length; i++) {
            inputList[i] = i;
        }

        int currentPosition = 0;
        int skipSize = 0;


        for (int j = 0; j < 64; j++) {
            for (Integer length : lengths) {
                int[] toReverse = new int[length];
                for (int i = 0; i < toReverse.length; i++) {
                    int index = (currentPosition + i) % inputList.length;
                    toReverse[i] = inputList[index];
                }
                ArrayUtils.reverse(toReverse);
                for (int i = 0; i < toReverse.length; i++) {
                    int index = (currentPosition + i) % inputList.length;
                    inputList[index] = toReverse[i];
                }

                currentPosition += length + skipSize++;
            }
        }

        int[] xors = new int[16];
        for (int i = 0; i < 16; i++) {
            int xor = inputList[i * 16];
            for (int j = 1; j < 16; j++) {
                xor ^= inputList[i * 16 + j];
            }

            xors[i] = xor;
        }

        StringBuilder hash = new StringBuilder();
        for (int xor : xors) {
            hash.append(String.format("%02X", xor));
        }

        return hash.toString().toLowerCase();
    }
}
