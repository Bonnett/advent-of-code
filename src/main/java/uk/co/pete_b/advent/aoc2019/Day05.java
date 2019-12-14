package uk.co.pete_b.advent.aoc2019;

import java.util.ArrayList;
import java.util.List;

public class Day05 {

    public static long getDiagnosticCode(final long input, final List<Long> operations) throws Exception {
        final List<Long> outputBuffer = new ArrayList<>();
        final OpCodeComputer computer = new OpCodeComputer(operations, () -> input, outputBuffer::add);
        computer.start();
        computer.join();

        return outputBuffer.get(outputBuffer.size() - 1);
    }
}
