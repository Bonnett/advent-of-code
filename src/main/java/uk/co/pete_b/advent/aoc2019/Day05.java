package uk.co.pete_b.advent.aoc2019;

import java.util.concurrent.*;

public class Day05 {

    public static int getDiagnosticCode(final int input, final int[] operations) throws Exception {
        final ExecutorService executor = Executors.newFixedThreadPool(1);
        final BlockingQueue<Integer> inputQueue = new ArrayBlockingQueue<>(2);
        final BlockingQueue<Integer> outputQueue = new ArrayBlockingQueue<>(20);

        final OpCodeComputer computer = new OpCodeComputer(operations, inputQueue, outputQueue);
        executor.execute(computer);
        inputQueue.put(input);
        executor.shutdown();
        executor.awaitTermination(1L, TimeUnit.SECONDS);

        final Integer[] output = outputQueue.toArray(new Integer[0]);
        return output[output.length - 1];
    }
}
