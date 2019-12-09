package uk.co.pete_b.advent.aoc2019;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Day09 {
    public static List<Long> getBoostKeyCode(final long input, final List<Long> operations) throws Exception {
        final ExecutorService executor = Executors.newFixedThreadPool(1);
        final BlockingQueue<Long> inputQueue = new ArrayBlockingQueue<>(20);
        final BlockingQueue<Long> outputQueue = new ArrayBlockingQueue<>(20);

        final OpCodeComputer computer = new OpCodeComputer(operations, inputQueue, outputQueue);
        executor.execute(computer);
        inputQueue.put(input);
        executor.shutdown();
        executor.awaitTermination(1L, TimeUnit.SECONDS);

        List<Long> output = new ArrayList<>();
        outputQueue.drainTo(output);
        return output;
    }
}
