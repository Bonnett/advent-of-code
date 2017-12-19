package uk.co.pete_b.advent.aoc2017;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Day18 {

    private static final String NUMBER_REGEX = "-?[0-9]+";

    public static long recoverFrequencyPart1(final String input) {
        final String[] instructions = input.split("\r?\n");
        final Map<String, Long> registers = new HashMap<>();

        int operationToExecute = 0;
        long lastPlayedSound = -1;
        boolean shouldExit = false;
        while (!shouldExit) {
            final String[] instruction = instructions[operationToExecute].split(" ");
            switch (instruction[0]) {
                case "snd": {
                    lastPlayedSound = registers.getOrDefault(instruction[1], 0L);
                    break;
                }
                case "set": {
                    final String action = instruction[2];
                    if (action.matches("[0-9]+")) {
                        registers.put(instruction[1], Long.valueOf(action));
                    } else {
                        registers.put(instruction[1], registers.getOrDefault(action, 0L));
                    }
                    break;
                }
                case "add": {
                    final String register = instruction[1];
                    final long startVal = registers.getOrDefault(register, 0L);
                    registers.put(instruction[1], startVal + Long.valueOf(instruction[2]));
                    break;
                }
                case "mul": {
                    final String register = instruction[1];
                    final long startVal = registers.getOrDefault(register, 0L);
                    final String action = instruction[2];
                    if (action.matches("[0-9]+")) {
                        registers.put(instruction[1], startVal * Long.valueOf(instruction[2]));
                    } else {
                        registers.put(instruction[1], startVal * registers.getOrDefault(action, 0L));
                    }
                    break;
                }
                case "mod": {
                    final String register = instruction[1];
                    final long startVal = registers.getOrDefault(register, 0L);
                    final String action = instruction[2];
                    if (action.matches("[0-9]+")) {
                        registers.put(instruction[1], startVal % Long.valueOf(instruction[2]));
                    } else {
                        registers.put(instruction[1], startVal % registers.getOrDefault(action, 0L));
                    }
                    break;
                }
                case "rcv": {
                    final String register = instruction[1];
                    final long startVal = registers.getOrDefault(register, 0L);
                    if (startVal > 0L) {
                        shouldExit = true;
                    }
                    break;
                }
                case "jgz": {
                    final String register = instruction[1];
                    final long startVal = registers.getOrDefault(register, 0L);
                    if (startVal > 0L) {
                        operationToExecute += Long.valueOf(instruction[2]) - 1;
                    }
                    break;
                }
            }
            operationToExecute++;
        }

        return lastPlayedSound;
    }

    public static long recoverFrequencyPart2(final String input) {
        final String[] instructions = input.split("\r?\n");
        final Map<String, Long> registersOne = new HashMap<>();
        registersOne.put("p", 0L);

        final Map<String, Long> registersTwo = new HashMap<>();
        registersTwo.put("p", 1L);

        final Queue<Long> queueOne = new LinkedList<>();
        final Queue<Long> queueTwo = new LinkedList<>();

        int progOneOperation = 0;
        int progTwoOperation = 0;

        int sendCount = 0;

        boolean progOneWaiting = false;
        boolean progTwoWaiting = false;

        while (true) {
            final String[] instructionProgOne = instructions[progOneOperation].split(" ");
            if (!progOneWaiting && !instructionProgOne[0].equals("rcv")) {
                switch (instructionProgOne[0]) {
                    case "snd": {
                        queueTwo.add(registersOne.getOrDefault(instructionProgOne[1], 0L));
                        break;
                    }
                    case "set": {
                        final String action = instructionProgOne[2];
                        if (action.matches(NUMBER_REGEX)) {
                            registersOne.put(instructionProgOne[1], Long.valueOf(action));
                        } else {
                            registersOne.put(instructionProgOne[1], registersOne.getOrDefault(action, 0L));
                        }
                        break;
                    }
                    case "add": {
                        final String register = instructionProgOne[1];
                        final long startVal = registersOne.getOrDefault(register, 0L);
                        final String action = instructionProgOne[2];
                        if (action.matches(NUMBER_REGEX)) {
                            registersOne.put(instructionProgOne[1], startVal + Long.valueOf(instructionProgOne[2]));
                        } else {
                            registersOne.put(instructionProgOne[1], startVal + registersOne.getOrDefault(action, 0L));
                        }
                        break;
                    }
                    case "mul": {
                        final String register = instructionProgOne[1];
                        final long startVal = registersOne.getOrDefault(register, 0L);
                        final String action = instructionProgOne[2];
                        if (action.matches(NUMBER_REGEX)) {
                            registersOne.put(instructionProgOne[1], startVal * Long.valueOf(instructionProgOne[2]));
                        } else {
                            registersOne.put(instructionProgOne[1], startVal * registersOne.getOrDefault(action, 0L));
                        }
                        break;
                    }
                    case "mod": {
                        final String register = instructionProgOne[1];
                        final long startVal = registersOne.getOrDefault(register, 0L);
                        final String action = instructionProgOne[2];
                        if (action.matches(NUMBER_REGEX)) {
                            registersOne.put(instructionProgOne[1], startVal % Long.valueOf(instructionProgOne[2]));
                        } else {
                            registersOne.put(instructionProgOne[1], startVal % registersOne.getOrDefault(action, 0L));
                        }
                        break;
                    }
                    case "jgz": {
                        final String register = instructionProgOne[1];
                        if (register.matches(NUMBER_REGEX)) {
                            final long startVal = Long.valueOf(register);
                            if (startVal > 0L) {
                                final String action = instructionProgOne[2];
                                if (action.matches(NUMBER_REGEX)) {
                                    progOneOperation += Long.valueOf(instructionProgOne[2]) - 1;
                                } else {
                                    progOneOperation += registersOne.getOrDefault(action, 0L) - 1;
                                }
                            }
                        } else {
                            final long startVal = registersOne.getOrDefault(register, 0L);
                            if (startVal > 0L) {
                                final String action = instructionProgOne[2];
                                if (action.matches(NUMBER_REGEX)) {
                                    progOneOperation += Long.valueOf(instructionProgOne[2]) - 1;
                                } else {
                                    progOneOperation += registersOne.getOrDefault(action, 0L) - 1;
                                }
                            }
                        }
                        break;
                    }
                }
                progOneOperation++;
            } else if (instructionProgOne[0].equals("rcv")) {
                if (queueOne.isEmpty()) {
                    progOneWaiting = true;
                } else {
                    final String register = instructionProgOne[1];
                    progOneWaiting = false;
                    registersOne.put(register, queueOne.poll());
                    progOneOperation++;
                }
            }

            final String[] instructionProgTwo = instructions[progTwoOperation].split(" ");
            if (!progTwoWaiting && !instructionProgTwo[0].equals("rcv")) {
                switch (instructionProgTwo[0]) {
                    case "snd": {
                        sendCount++;
                        queueOne.add(registersTwo.getOrDefault(instructionProgTwo[1], 0L));
                    }
                    break;
                    case "set": {
                        final String action = instructionProgTwo[2];
                        if (action.matches(NUMBER_REGEX)) {
                            registersTwo.put(instructionProgTwo[1], Long.valueOf(action));
                        } else {
                            registersTwo.put(instructionProgTwo[1], registersTwo.getOrDefault(action, 0L));
                        }
                        break;
                    }
                    case "add": {
                        final String register = instructionProgTwo[1];
                        final long startVal = registersTwo.getOrDefault(register, 0L);
                        final String action = instructionProgTwo[2];
                        if (action.matches(NUMBER_REGEX)) {
                            registersTwo.put(instructionProgTwo[1], startVal + Long.valueOf(instructionProgTwo[2]));
                        } else {
                            registersTwo.put(instructionProgTwo[1], startVal + registersTwo.getOrDefault(action, 0L));
                        }
                        break;
                    }
                    case "mul": {
                        final String register = instructionProgTwo[1];
                        final long startVal = registersTwo.getOrDefault(register, 0L);
                        final String action = instructionProgTwo[2];
                        if (action.matches(NUMBER_REGEX)) {
                            registersTwo.put(instructionProgTwo[1], startVal * Long.valueOf(instructionProgTwo[2]));
                        } else {
                            registersTwo.put(instructionProgTwo[1], startVal * registersTwo.getOrDefault(action, 0L));
                        }
                        break;
                    }
                    case "mod": {
                        final String register = instructionProgTwo[1];
                        final long startVal = registersTwo.getOrDefault(register, 0L);
                        final String action = instructionProgTwo[2];
                        if (action.matches(NUMBER_REGEX)) {
                            registersTwo.put(instructionProgTwo[1], startVal % Long.valueOf(instructionProgTwo[2]));
                        } else {
                            registersTwo.put(instructionProgTwo[1], startVal % registersTwo.getOrDefault(action, 0L));
                        }
                        break;
                    }
                    case "jgz": {
                        final String register = instructionProgTwo[1];
                        if (register.matches(NUMBER_REGEX)) {
                            final long startVal = Long.valueOf(register);
                            if (startVal > 0L) {
                                final String action = instructionProgTwo[2];
                                if (action.matches(NUMBER_REGEX)) {
                                    progTwoOperation += Long.valueOf(instructionProgTwo[2]) - 1;
                                } else {
                                    progTwoOperation += registersTwo.getOrDefault(action, 0L) - 1;
                                }
                            }
                        } else {
                            final long startVal = registersTwo.getOrDefault(register, 0L);
                            if (startVal > 0L) {
                                final String action = instructionProgTwo[2];
                                if (action.matches(NUMBER_REGEX)) {
                                    progTwoOperation += Long.valueOf(instructionProgTwo[2]) - 1;
                                } else {
                                    progTwoOperation += registersTwo.getOrDefault(action, 0L) - 1;
                                }
                            }
                        }
                        break;
                    }
                }
                progTwoOperation++;
            } else if (instructionProgTwo[0].equals("rcv")) {
                if (queueTwo.isEmpty()) {
                    progTwoWaiting = true;
                } else {
                    final String register = instructionProgTwo[1];
                    progTwoWaiting = false;
                    registersTwo.put(register, queueTwo.poll());
                    progTwoOperation++;
                }
            }

            if (progTwoWaiting && progOneWaiting) {
                break;
            }
        }

        return sendCount;
    }
}
