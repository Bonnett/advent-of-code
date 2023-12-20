package uk.co.pete_b.advent.aoc2023;

import uk.co.pete_b.advent.utils.Maths;

import java.util.*;

public class Day20 {
    public static long calculatePlusProduct(final List<String> componentLayout) {
        final Map<String, Component> components = getComponents(componentLayout);

        long highPulses = 0;
        long lowPulses = 0;

        for (int i = 0; i < 1000; i++) {
            final Deque<OutputPulse> processingChain = new ArrayDeque<>();
            // Push the button
            lowPulses++;
            final OutputPulse startState = components.get("broadcaster").receiveSignal("button", Pulse.LOW);
            processingChain.add(startState);

            while (!processingChain.isEmpty()) {
                final OutputPulse output = processingChain.poll();
                for (Map.Entry<String, Optional<Pulse>> input : output.output.entrySet()) {
                    if (input.getValue().isEmpty()) {
                        continue;
                    }
                    final Pulse pulse = input.getValue().get();
                    if (pulse == Pulse.HIGH) {
                        highPulses++;
                    } else {
                        lowPulses++;
                    }
                    final OutputPulse newState = components.get(input.getKey()).receiveSignal(output.source, pulse);
                    processingChain.add(newState);
                }
            }
        }

        return highPulses * lowPulses;
    }

    private static Map<String, Component> getComponents(List<String> componentLayout) {
        final Map<String, Component> components = new HashMap<>();
        for (String layout : componentLayout) {
            final String[] parts = layout.split(" -> ");
            final List<String> connectedComponents = Arrays.stream(parts[1].split(", ")).toList();
            if (parts[0].startsWith("&")) {
                final String partName = parts[0].substring(1);
                components.put(partName, new Conjunction(partName, connectedComponents));
            } else if (parts[0].startsWith("%")) {
                final String partName = parts[0].substring(1);
                components.put(partName, new FlipFlop(partName, connectedComponents));
            } else if (parts[0].equals("broadcaster")) {
                components.put(parts[0], new Broadcaster(parts[0], connectedComponents));
            }
        }

        for (String layout : componentLayout) {
            final String[] parts = layout.split(" -> ");
            final List<String> connectedComponents = Arrays.stream(parts[1].split(", ")).toList();
            final String partName = (parts[0].matches("[&%].*")) ? parts[0].substring(1) : parts[0];
            for (String component : connectedComponents) {
                if (components.containsKey(component)) {
                    components.get(component).addInputComponent(partName);
                } else {
                    components.put(component, new Sink(component, Collections.emptyList()));
                    components.get(component).addInputComponent(partName);
                }
            }
        }
        return components;
    }

    public static long calculatePlusesTillRxTurnsOn(final List<String> componentLayout) {
        final Map<String, Component> components = getComponents(componentLayout);

        int cyclesNeeded = 0;
        final Component target = components.get("rx");
        final String sourceComponentName = target.getInputComponents().get(0);
        final Conjunction sourceComponent = (Conjunction) components.get(sourceComponentName);
        cyclesNeeded += components.get(sourceComponentName).getInputComponents().size();

        final Map<String, Long> cycleSizes = new HashMap<>();

        int buttonPresses = 0;
        while (cycleSizes.size() != cyclesNeeded) {
            buttonPresses++;

            final Deque<OutputPulse> processingChain = new ArrayDeque<>();
            final OutputPulse startState = components.get("broadcaster").receiveSignal("button", Pulse.LOW);
            processingChain.add(startState);

            while (!processingChain.isEmpty()) {
                final OutputPulse output = processingChain.poll();
                if (output.source.equals(sourceComponentName)) {

                    for (Map.Entry<String, Pulse> inputs : sourceComponent.currentInputState.entrySet())
                    {
                        if (inputs.getValue() == Pulse.HIGH)
                        {
                            cycleSizes.put(inputs.getKey(), (long) buttonPresses);
                        }
                    }
                }

                for (Map.Entry<String, Optional<Pulse>> input : output.output.entrySet()) {
                    if (input.getValue().isEmpty()) {
                        continue;
                    }
                    final OutputPulse newState = components.get(input.getKey()).receiveSignal(output.source, input.getValue().get());
                    processingChain.add(newState);
                }
            }
        }

        return Maths.lcm(cycleSizes.values().stream().mapToLong(Long::valueOf).toArray());
    }

    private record OutputPulse(String source, Map<String, Optional<Pulse>> output) {
    }

    private static abstract class Component {

        private final String name;

        private final List<String> outputComponents;

        private final List<String> inputComponents;

        public Component(final String name, final List<String> outputComponents) {
            this.name = name;
            this.outputComponents = outputComponents;
            this.inputComponents = new ArrayList<>();
        }

        public String getName() {
            return name;
        }

        public List<String> getInputComponents() {
            return inputComponents;
        }

        public List<String> getOutputComponents() {
            return outputComponents;
        }

        public void addInputComponent(final String inputComponent) {
            this.inputComponents.add(inputComponent);
        }

        public abstract OutputPulse receiveSignal(final String inputSource, final Pulse input);
    }

    private static class Sink extends Component {
        public Sink(final String name, final List<String> outputComponents) {
            super(name, outputComponents);
        }

        @Override
        public OutputPulse receiveSignal(String inputSource, Pulse input) {
            return new OutputPulse(this.getName(), Collections.emptyMap());
        }
    }

    private static class Broadcaster extends Component {

        public Broadcaster(final String name, final List<String> outputComponents) {
            super(name, outputComponents);
        }


        @Override
        public OutputPulse receiveSignal(final String inputSource, Pulse input) {
            final Optional<Pulse> result = Optional.of(input);
            final Map<String, Optional<Pulse>> outputMap = new HashMap<>();
            for (String outputComponent : this.getOutputComponents()) {
                outputMap.put(outputComponent, result);
            }

            return new OutputPulse(this.getName(), outputMap);
        }
    }

    private static class Conjunction extends Component {

        final Map<String, Pulse> currentInputState = new HashMap<>();

        public Conjunction(final String name, final List<String> outputComponents) {
            super(name, outputComponents);
        }

        @Override
        public void addInputComponent(final String inputComponent) {
            super.addInputComponent(inputComponent);
            this.currentInputState.put(inputComponent, Pulse.LOW);
        }

        @Override
        public OutputPulse receiveSignal(final String inputSource, Pulse input) {
            this.currentInputState.put(inputSource, input);
            final Optional<Pulse> result = getOutputResult();
            final Map<String, Optional<Pulse>> outputMap = new HashMap<>();
            for (String outputComponent : this.getOutputComponents()) {
                outputMap.put(outputComponent, result);
            }

            return new OutputPulse(this.getName(), outputMap);
        }

        private Optional<Pulse> getOutputResult() {
            if (this.currentInputState.values().stream().anyMatch(x -> x == Pulse.LOW)) {
                return Optional.of(Pulse.HIGH);
            }
            return Optional.of(Pulse.LOW);
        }

    }

    private static class FlipFlop extends Component {

        private State state = State.OFF;

        public FlipFlop(final String name, final List<String> outputComponents) {
            super(name, outputComponents);
        }

        @Override
        public OutputPulse receiveSignal(final String inputSource, Pulse input) {
            final Optional<Pulse> result = processSignal(input);
            final Map<String, Optional<Pulse>> outputMap = new HashMap<>();
            for (String outputComponent : this.getOutputComponents()) {
                outputMap.put(outputComponent, result);
            }

            return new OutputPulse(this.getName(), outputMap);
        }

        private Optional<Pulse> processSignal(final Pulse input) {
            if (input == Pulse.HIGH) {
                return Optional.empty();
            } else {
                switch (this.state) {
                    case ON -> {
                        this.state = State.OFF;
                        return Optional.of(Pulse.LOW);
                    }
                    case OFF -> {
                        this.state = State.ON;
                        return Optional.of(Pulse.HIGH);
                    }
                }
            }

            return Optional.empty();
        }
    }

    private enum Pulse {
        HIGH, LOW
    }

    private enum State {
        ON, OFF
    }
}
