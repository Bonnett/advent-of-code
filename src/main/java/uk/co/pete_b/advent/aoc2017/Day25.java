package uk.co.pete_b.advent.aoc2017;

import uk.co.pete_b.advent.Utils.Direction;

import java.util.HashMap;
import java.util.Map;

public class Day25 {

    public static int getChecksum(final String input) {
        final String[] inputLines = input.split("\r?\n");
        int lineCounter = 0;

        String currentState = inputLines[lineCounter++].substring("Begin in state ".length()).substring(0, 1);
        final int checksumAfter = Integer.valueOf(inputLines[lineCounter++].substring("Perform a diagnostic checksum after ".length()).split(" ")[0]);
        lineCounter++;
        final Map<String, State> states = new HashMap<>();
        while (lineCounter < inputLines.length) {
            final String name = inputLines[lineCounter++].substring("In state ".length()).substring(0, 1);
            final State state = new State();
            {
                lineCounter++;
                final int valueToWrite = Integer.valueOf(inputLines[lineCounter++].substring("    - Write the value ".length()).substring(0, 1));
                final String direction = inputLines[lineCounter++].substring("    - Move one slot to the ".length()).replaceAll("\\.", "");
                final String nextState = inputLines[lineCounter++].substring("    - Continue with state ".length()).substring(0, 1);
                state.setMovementWhenZero(nextState, direction, valueToWrite);
            }
            {
                lineCounter++;
                final int valueToWrite = Integer.valueOf(inputLines[lineCounter++].substring("    - Write the value ".length()).substring(0, 1));
                final String direction = inputLines[lineCounter++].substring("    - Move one slot to the ".length()).replaceAll("\\.", "");
                final String nextState = inputLines[lineCounter++].substring("    - Continue with state ".length()).substring(0, 1);
                state.setMovementWhenOne(nextState, direction, valueToWrite);
            }

            states.put(name, state);
            lineCounter++;
        }

        final Map<Integer, Integer> ticker = new HashMap<>();
        int cursorPosition = 0;
        for (int i = 0; i < checksumAfter; i++) {
            State state = states.get(currentState);
            int currValue = ticker.getOrDefault(cursorPosition, 0);
            Movement m;
            if (currValue == 0) {
                m = state.movementWhenZero;
            } else {
                m = state.movementWhenOne;
            }
            ticker.put(cursorPosition, m.valueToWrite);
            currentState = m.nextState;
            cursorPosition += (m.moveDirection == Direction.RIGHT) ? 1 : -1;
        }

        return ticker.values().stream().mapToInt(x -> x).sum();
    }

    private static class State {
        private Movement movementWhenZero;
        private Movement movementWhenOne;

        private void setMovementWhenZero(String state, String directionToMove, int valueToWrite) {
            movementWhenZero = new Movement(state, Direction.fromString(directionToMove), valueToWrite);
        }

        private void setMovementWhenOne(String state, String directionToMove, int valueToWrite) {
            movementWhenOne = new Movement(state, Direction.fromString(directionToMove), valueToWrite);
        }
    }

    private static class Movement {
        private final String nextState;
        private final Direction moveDirection;
        private final int valueToWrite;

        Movement(final String nextState, final Direction moveDirection, final int valueToWrite) {
            this.nextState = nextState;
            this.moveDirection = moveDirection;
            this.valueToWrite = valueToWrite;
        }

    }
}
