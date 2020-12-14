package uk.co.pete_b.advent.aoc2020;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class Day14 {

    private static final char X = 'X';
    private static final char ONE = '1';

    public static long sumMemoryValuesVersionOne(final String instructions) {
        final String[] operations = instructions.split("\n");

        final Map<Integer, Long> memory = new HashMap<>();
        final Map<Integer, Character> bitMasks = new HashMap<>();

        for (String operation : operations) {
            final String value = operation.substring(operation.indexOf(" = ") + 3);

            if (operation.startsWith("mask")) {
                bitMasks.clear();
                final char[] mask = value.toCharArray();
                for (int i = 0; i < mask.length; i++) {
                    if (mask[i] != X) {
                        bitMasks.put(i, mask[i]);
                    }
                }
            } else {
                final int memoryIndex = Integer.parseInt(operation.substring(4, operation.indexOf("]")));
                final char[] memoryValueBinary = StringUtils.leftPad(Long.toBinaryString(Long.parseLong(value)), 36, "0").toCharArray();
                for (Map.Entry<Integer, Character> bitMask : bitMasks.entrySet()) {
                    memoryValueBinary[bitMask.getKey()] = bitMask.getValue();
                }
                memory.put(memoryIndex, Long.parseLong(new String(memoryValueBinary), 2));
            }
        }

        return memory.values().stream().mapToLong(Long::longValue).sum();
    }

    public static long sumMemoryValuesVersionTwo(final String instructions) {
        final String[] operations = instructions.split("\n");

        final Map<String, Long> memory = new HashMap<>();
        char[] bitmask = new char[36];

        for (String operation : operations) {
            final String value = operation.substring(operation.indexOf(" = ") + 3);

            if (operation.startsWith("mask")) {
                bitmask = value.toCharArray();
            } else {
                final Long valueToSet = Long.parseLong(value);
                final int memoryIndex = Integer.parseInt(operation.substring(4, operation.indexOf("]")));
                final char[] addressBinary = StringUtils.leftPad(Integer.toBinaryString(memoryIndex), 36, "0").toCharArray();
                for (int i = 0; i < addressBinary.length; i++) {
                    if (bitmask[i] == ONE) {
                        addressBinary[i] = ONE;
                    } else if (bitmask[i] == X) {
                        addressBinary[i] = X;
                    }
                }

                final Set<String> addressesToSet = new HashSet<>();
                calculateAddressesToSet(addressesToSet, addressBinary);

                for (String address : addressesToSet)
                {
                    memory.put(address, valueToSet);
                }
            }
        }

        return memory.values().stream().mapToLong(Long::longValue).sum();
    }

    private static void calculateAddressesToSet(final Set<String> possibleAddresses, char[] inputMask) {
        for (int i=0; i<inputMask.length; i++)
        {
            if (inputMask[i] == X)
            {
                final char[] maskClone = Arrays.copyOf(inputMask, inputMask.length);
                maskClone[i] = '0';
                inputMask[i] = ONE;
                calculateAddressesToSet(possibleAddresses, maskClone);
                calculateAddressesToSet(possibleAddresses, inputMask);
                return;
            }
        }

        possibleAddresses.add(new String(inputMask));
    }
}
