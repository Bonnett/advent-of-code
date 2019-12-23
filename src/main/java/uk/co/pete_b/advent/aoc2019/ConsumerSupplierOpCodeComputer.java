package uk.co.pete_b.advent.aoc2019;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ConsumerSupplierOpCodeComputer extends AbstractOpCodeComputer {
    private final Supplier<Long> input;
    private final Consumer<Long> output;

    public ConsumerSupplierOpCodeComputer(final List<Long> operations, final Supplier<Long> input, final Consumer<Long> output) {
        super(operations);
        this.input = input;
        this.output = output;
    }

    @Override
    public Long getInputValue() {
        return this.input.get();
    }

    @Override
    public void setOutputValue(final Long value) {
        this.output.accept(value);
    }
}
