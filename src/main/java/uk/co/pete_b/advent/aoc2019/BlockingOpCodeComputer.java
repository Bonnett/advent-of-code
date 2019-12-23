package uk.co.pete_b.advent.aoc2019;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingOpCodeComputer extends AbstractOpCodeComputer {
    private final BlockingQueue<Long> input;
    private final BlockingQueue<Long> output;

    public BlockingOpCodeComputer(final List<Long> operations, BlockingQueue<Long> input, BlockingQueue<Long> output) {
        super(operations);
        this.input = input;
        this.output = output;
    }

    public BlockingQueue<Long> getInput() {
        return this.input;
    }

    public BlockingQueue<Long> getOutput() {
        return this.output;
    }

    @Override
    public Long getInputValue() throws Exception {
        Long val = this.input.poll(50, TimeUnit.MILLISECONDS);
        return val == null ? -1L : val;
    }

    @Override
    public void setOutputValue(Long value) throws Exception {
        this.output.put(value);
    }
}
