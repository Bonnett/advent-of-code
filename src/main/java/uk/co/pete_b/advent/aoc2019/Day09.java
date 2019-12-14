package uk.co.pete_b.advent.aoc2019;

import java.util.ArrayList;
import java.util.List;

public class Day09 {
    public static List<Long> getBoostKeyCode(final long input, final List<Long> operations) throws Exception {
        final List<Long> outputBuffer = new ArrayList<>();
        final OpCodeComputer computer = new OpCodeComputer(operations, () -> input, outputBuffer::add);
        computer.start();
        computer.join();

        return outputBuffer;
    }
}
