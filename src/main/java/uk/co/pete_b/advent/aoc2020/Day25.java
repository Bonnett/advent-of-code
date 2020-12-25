package uk.co.pete_b.advent.aoc2020;

public class Day25 {
    public static long findEncryptionKey(final String input) {
        final String[] keys = input.split("\n");
        final long cardPublicKey = Long.parseLong(keys[0]);
        final long doorPublicKey = Long.parseLong(keys[1]);

        long startValue = 7;
        long doorLoopSize = -1;
        long iteration = 0;
        while (doorLoopSize == -1) {
            startValue = (7 * startValue) % 20201227;
            iteration++;

            if (startValue == doorPublicKey) {
                doorLoopSize = iteration;
            }
        }

        long encryptionKey = cardPublicKey;
        for (long i = 0; i < doorLoopSize; i++) {
            encryptionKey = (cardPublicKey * encryptionKey) % 20201227;
        }

        return encryptionKey;
    }
}
