package uk.co.pete_b.advent.aoc2022;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day19 {

    private static final Pattern ROBOT_PATTERN = Pattern.compile("Blueprint ([^:]+): Each ore robot costs ([^ ]+) ore. Each clay robot costs ([^ ]+) ore. Each obsidian robot costs ([^ ]+) ore and ([^ ]+) clay. Each geode robot costs ([^ ]+) ore and ([^ ]+) obsidian.");

    private static final int TIME_LIMIT = 24;
    private static final int TIME_LIMIT_REDUCED_LIST = 32;
    public static final int ORE = 0;
    public static final int CLAY = 1;
    public static final int OBSIDIAN = 2;
    public static final int GEODE = 3;

    public static int calculateQualityTotal(final List<String> blueprintSetup) {
        return getBlueprints(blueprintSetup).parallelStream()
                .map(blueprint -> calculateMaxGeodes(blueprint, TIME_LIMIT))
                .mapToInt(result -> result.getKey() * result.getValue())
                .sum();
    }

    public static int calculateQualityTotalWithReducedList(final List<String> blueprintSetup) {

        return getBlueprints(blueprintSetup).parallelStream().filter(x -> x.id <= 3)
                .map(blueprint -> calculateMaxGeodes(blueprint, TIME_LIMIT_REDUCED_LIST))
                .mapToInt(Pair::getValue)
                .reduce(1, (a, b) -> a * b);
    }

    private static List<Blueprint> getBlueprints(List<String> blueprintSetup) {
        final List<Blueprint> blueprints = new ArrayList<>();
        for (String blueprint : blueprintSetup) {
            final Matcher m = ROBOT_PATTERN.matcher(blueprint);
            if (m.matches()) {
                int blueprintId = Integer.parseInt(m.group(1));
                int oreRobotCost = Integer.parseInt(m.group(2));
                int clayRobotCost = Integer.parseInt(m.group(3));
                int obsidianRobotCostOre = Integer.parseInt(m.group(4));
                int obsidianRobotCostClay = Integer.parseInt(m.group(5));
                int geodeRobotCostOre = Integer.parseInt(m.group(6));
                int geodeRobotCostObsidian = Integer.parseInt(m.group(7));

                // ore, clay, obs, geode
                final Robot oreRobot = new Robot(ORE, new int[]{oreRobotCost, 0, 0, 0});
                final Robot clayRobot = new Robot(CLAY, new int[]{clayRobotCost, 0, 0, 0});
                final Robot obsidianRobot = new Robot(OBSIDIAN, new int[]{obsidianRobotCostOre, obsidianRobotCostClay, 0, 0});
                final Robot geodeRobot = new Robot(GEODE, new int[]{geodeRobotCostOre, 0, geodeRobotCostObsidian, 0});

                blueprints.add(new Blueprint(blueprintId, oreRobot, clayRobot, obsidianRobot, geodeRobot));
            }
        }
        return blueprints;
    }

    private static Pair<Integer, Integer> calculateMaxGeodes(final Blueprint blueprint, final int timeLimit) {
        final int[] initialRobots = new int[]{1, 0, 0, 0};

        final RobotState initialState = new RobotState(initialRobots, new int[]{0, 0, 0, 0});
        Set<RobotState> states = new HashSet<>();
        states.add(initialState);

        for (int i = 1; i <= timeLimit; i++) {
            final Set<RobotState> newStates = new HashSet<>();

            for (RobotState currentState : states) {
                final int[] resources = currentState.resources;
                // What robots can we create
                final Set<Integer> possibleNewRobots = new HashSet<>();

                // Don't bother for the last minute
                if (i < timeLimit) {
                    for (int z = 0; z < blueprint.robotProducers.length; z++) {
                        // Don't create these when they won't be of use in time
                        if ((z == ORE && i > (timeLimit - 4)) || (z == CLAY && i > (timeLimit - 3)) || (z == OBSIDIAN && i > (timeLimit - 2))) {
                            continue;
                        }
                        if (blueprint.robotProducers[z].canProduceNewRobot(resources)) {
                            possibleNewRobots.add(z);
                        }
                    }
                }

                // Prioritise Geode creation
                if (possibleNewRobots.contains(GEODE)) {
                    possibleNewRobots.remove(OBSIDIAN);
                    possibleNewRobots.remove(CLAY);
                    possibleNewRobots.remove(ORE);
                }

                if (possibleNewRobots.contains(OBSIDIAN)) {
                    possibleNewRobots.remove(CLAY);
                    possibleNewRobots.remove(ORE);
                }

                // Update what resources we have
                for (int x = 0; x < resources.length; x++) {
                    resources[x] += currentState.robots[x];
                }

                // Ignore states where we're running out of time
                if (i == (timeLimit - 1) && currentState.robots[GEODE] == 0 && !possibleNewRobots.contains(GEODE)) {
                    continue;
                }

                if (i == (timeLimit - 2) && currentState.robots[GEODE] == 0 && currentState.robots[OBSIDIAN] == 0 &&
                        !(possibleNewRobots.contains(GEODE) || possibleNewRobots.contains(OBSIDIAN))) {
                    continue;
                }

                if (i == (timeLimit - 3) && currentState.robots[GEODE] == 0 && currentState.robots[OBSIDIAN] == 0 && currentState.robots[CLAY] == 0
                        && !(possibleNewRobots.contains(GEODE) || possibleNewRobots.contains(OBSIDIAN) || possibleNewRobots.contains(CLAY))) {
                    continue;
                }

                // Add the current state (in case we just want ore)
                if (!possibleNewRobots.contains(GEODE)) {
                    newStates.add(new RobotState(currentState.robots, resources));
                }

                // Add any other new robots
                for (Integer newRobot : possibleNewRobots) {
                    final int[] robotCount = Arrays.copyOf(currentState.robots, currentState.robots.length);
                    final int[] newResources = Arrays.copyOf(currentState.resources, currentState.resources.length);

                    robotCount[newRobot] += 1;

                    final Robot robot = blueprint.robotProducers[newRobot];
                    for (int y = 0; y < newResources.length; y++) {
                        newResources[y] -= robot.resourceCost[y];
                    }

                    if (i == (timeLimit - 1) && robotCount[GEODE] == 0) {
                        continue;
                    }

                    if (i == (timeLimit - 2) && robotCount[GEODE] == 0 && robotCount[OBSIDIAN] == 0) {
                        continue;
                    }

                    if (i == (timeLimit - 3) && robotCount[GEODE] == 0 && robotCount[OBSIDIAN] == 0 && robotCount[CLAY] == 0) {
                        continue;
                    }

                    newStates.add(new RobotState(robotCount, newResources));
                }
            }

            states = newStates;
        }

        return Pair.of(blueprint.id, states.stream().mapToInt(x -> x.resources[GEODE]).max().orElse(0));
    }

    private record Robot(int produces, int[] resourceCost) {
        public boolean canProduceNewRobot(final int[] currentResources) {
            return resourceCost[0] <= currentResources[0] && resourceCost[1] <= currentResources[1] && resourceCost[2] <= currentResources[2];
        }
    }

    private static class Blueprint {
        private final int id;

        private final Robot[] robotProducers;

        public Blueprint(final int id, final Robot oreRobot, final Robot clayRobot, final Robot obsidianRobot, final Robot geodeRobot) {

            this.id = id;

            this.robotProducers = new Robot[4];
            this.robotProducers[geodeRobot.produces] = geodeRobot;
            this.robotProducers[obsidianRobot.produces] = obsidianRobot;
            this.robotProducers[clayRobot.produces] = clayRobot;
            this.robotProducers[oreRobot.produces] = oreRobot;
        }
    }

    private record RobotState(int[] robots, int[] resources) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            RobotState that = (RobotState) o;

            return new EqualsBuilder().append(robots, that.robots).append(resources, that.resources).isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37).append(robots).append(resources).toHashCode();
        }

        @Override
        public String toString() {
            return "Robots=" + Arrays.toString(robots) + "; Resources=" + Arrays.toString(resources);
        }
    }
}
