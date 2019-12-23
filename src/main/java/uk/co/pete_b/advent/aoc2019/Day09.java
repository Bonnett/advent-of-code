package uk.co.pete_b.advent.aoc2019;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Day09 {
    public static List<Long> getBoostKeyCode(final long input, final List<Long> operations) throws InterruptedException {
        final List<Long> outputBuffer = new ArrayList<>();
        final ConsumerSupplierOpCodeComputer computer = new ConsumerSupplierOpCodeComputer(operations, () -> input, outputBuffer::add);
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(computer);
        executor.shutdown();
        executor.awaitTermination(2L, TimeUnit.SECONDS);

        return outputBuffer;
    }
}
