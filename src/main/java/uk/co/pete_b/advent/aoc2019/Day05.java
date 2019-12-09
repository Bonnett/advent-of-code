package uk.co.pete_b.advent.aoc2019;

import java.util.List;
import java.util.concurrent.*;

public class Day05 {

    public static long getDiagnosticCode(final long input, final List<Long> operations) throws Exception {
        final ExecutorService executor = Executors.newFixedThreadPool(1);
        final BlockingQueue<Long> inputQueue = new ArrayBlockingQueue<>(2);
        final BlockingQueue<Long> outputQueue = new ArrayBlockingQueue<>(20);

        final OpCodeComputer computer = new OpCodeComputer(operations, inputQueue, outputQueue);
        executor.execute(computer);
        inputQueue.put(input);
        executor.shutdown();
        executor.awaitTermination(1L, TimeUnit.SECONDS);

        final Long[] output = outputQueue.toArray(new Long[0]);
        return output[output.length - 1];
    }
}
