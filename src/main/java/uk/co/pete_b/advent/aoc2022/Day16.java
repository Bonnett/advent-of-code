package uk.co.pete_b.advent.aoc2022;

import org.paukov.combinatorics3.Generator;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toSet;

// Not a fan of this puzzle at all :(
public class Day16 {
    private static final Pattern VALVE_PATTERN = Pattern.compile("Valve ([^ ]+) has flow rate=([^;]+); tunnels? leads? to valves? (.*)");

    public static final int TIME_LIMIT_ALONE = 30;

    public static final int TIME_LIMIT_WITH_ELEPHANT = 26;

    // This is heavily based on https://github.com/dirk527/aoc2021/blob/b0835e9803f93a1aeba214d8eb585f6a4ff6927a/src/aoc2022/Day16.java
    public static int maxPressureReleasedAlone(final List<String> valveSetup) {
        final Map<String, Valve> start = buildCaveMap(valveSetup);

        return maxPressureReleased(new ValveState(start.get("AA"), TIME_LIMIT_ALONE, 0, new HashSet<>()));
    }

    private static int maxPressureReleased(ValveState valveState) {
        final Deque<ValveState> states = new ArrayDeque<>();
        states.add(valveState);

        int maxPressureReleased = Integer.MIN_VALUE;
        while (!states.isEmpty()) {
            final ValveState state = states.poll();
            for (Map.Entry<Valve, Integer> route : state.currentValve.routes.entrySet()) {
                final int timeLeft = state.minutes - route.getValue();
                final Valve nextValve = route.getKey();
                if (timeLeft > 0 && !state.valvesOpen.contains(nextValve.valveName)) {
                    final Set<String> valvesOpened = new HashSet<>(state.valvesOpen);
                    valvesOpened.add(nextValve.valveName);
                    int newlyOpened = timeLeft * nextValve.getFlowRate();
                    states.add(new ValveState(nextValve, timeLeft, state.currentPressure + newlyOpened, valvesOpened));
                }
            }

            if (state.currentPressure > maxPressureReleased) {
                maxPressureReleased = state.currentPressure;
            }
        }

        return maxPressureReleased;
    }

    // This heavily based on https://github.com/jerchende/advent-of-code-2022/commit/596019a48f958f469b64ac8a2776881391c6f403
    public static int maxPressureReleasedWithElephant(final List<String> valveSetup) {
        final Map<String, Valve> start = buildCaveMap(valveSetup);
        final Set<String> targets = start.entrySet().stream()
                .filter(valve -> !valve.getKey().equals("AA"))
                .filter(valve -> valve.getValue().flowRate > 0)
                .map(Map.Entry::getKey)
                .collect(toSet());

        final Map<Set<String>, Integer> previousTotals = new HashMap<>();

        return Generator.combination(targets)
                .simple(targets.size() / 2)
                .stream()
                .parallel()
                .mapToInt(valvesToVisit -> {
                    final Set<String> mySet = new HashSet<>(valvesToVisit);
                    final int myValves;
                    if (previousTotals.containsKey(mySet)) {
                        myValves = previousTotals.get(mySet);
                    } else {
                        myValves = maxPressureReleased(new ValveState(start.get("AA"), TIME_LIMIT_WITH_ELEPHANT, 0, mySet));

                        previousTotals.put(mySet, myValves);
                    }

                    final Set<String> elephantSet = targets.stream().filter(not(valvesToVisit::contains)).collect(toSet());
                    final int elephantValves;
                    if (previousTotals.containsKey(elephantSet)) {
                        elephantValves = previousTotals.get(elephantSet);
                    } else {
                        elephantValves = maxPressureReleased(new ValveState(start.get("AA"), TIME_LIMIT_WITH_ELEPHANT, 0, new HashSet<>(elephantSet)));
                        previousTotals.put(elephantSet, elephantValves);
                    }

                    return myValves + elephantValves;
                }).max().orElse(0);
    }

    private static Map<String, Valve> buildCaveMap(List<String> valveSetup) {
        final Map<String, Valve> valves = new HashMap<>();

        final Set<Valve> nonZeroValves = new HashSet<>();

        for (String valveLine : valveSetup) {
            final Matcher matcher = VALVE_PATTERN.matcher(valveLine);
            if (matcher.matches()) {
                final String valveName = matcher.group(1);
                final int flowRate = Integer.parseInt(matcher.group(2));
                Valve valve;
                if (valves.containsKey(valveName)) {
                    valve = valves.get(valveName);
                    valve.setFlowRate(flowRate);
                } else {
                    valve = new Valve(valveName, flowRate);
                }
                valves.put(valveName, valve);
                Arrays.stream(matcher.group(3).split(", ")).forEach(v -> {
                    if (valves.containsKey(v)) {
                        valves.get(v).addTunnel(valve);
                        valve.getTunnels().add(valves.get(v));
                    } else {
                        Valve newValve = new Valve(v);
                        newValve.addTunnel(valve);
                        valve.getTunnels().add(newValve);
                        valves.put(v, newValve);
                    }
                });

                if (flowRate > 0) {
                    nonZeroValves.add(valve);
                }
            }
        }

        // Precompute distances from each valve to any of the other non-zero valves
        for (Valve v : valves.values()) {
            v.findPaths(nonZeroValves);
        }

        // Precompute distances from the start valve to any of the other non-zero valves as well
        final Valve start = valves.get("AA");
        start.findPaths(nonZeroValves);

        return valves;
    }

    private static class Valve {
        private final String valveName;

        private int flowRate;

        private final Set<Valve> tunnels;

        private final Map<Valve, Integer> routes = new HashMap<>();

        public Valve(final String valveName) {
            this.valveName = valveName;
            this.tunnels = new HashSet<>();
        }

        public Valve(final String valveName, int flowRate) {
            this.valveName = valveName;
            this.flowRate = flowRate;
            this.tunnels = new HashSet<>();
        }

        private void addTunnel(final Valve tunnelName) {
            this.tunnels.add(tunnelName);
        }

        public int getFlowRate() {
            return flowRate;
        }

        public void setFlowRate(int flowRate) {
            this.flowRate = flowRate;
        }

        public Set<Valve> getTunnels() {
            return tunnels;
        }

        @Override
        public String toString() {
            return valveName;
        }

        public void findPaths(final Set<Valve> targets) {
            final Deque<Valve> paths = new ArrayDeque<>();
            final Map<Valve, Integer> distances = new HashMap<>();
            final Set<Valve> visited = new HashSet<>();
            paths.add(this);
            distances.put(this, 0);

            while (!paths.isEmpty() && routes.size() < targets.size()) {
                final Valve currentValve = paths.poll();
                visited.add(currentValve);
                Integer curDist = distances.get(currentValve);
                if (currentValve != this && targets.contains(currentValve)) {
                    routes.put(currentValve, curDist + 1);
                }

                for (Valve v : currentValve.tunnels) {
                    if (!visited.contains(v)) {
                        paths.add(v);
                        distances.put(v, curDist + 1);
                    }
                }
            }
        }
    }

    record ValveState(Valve currentValve, int minutes, int currentPressure, Set<String> valvesOpen) {
    }
}
