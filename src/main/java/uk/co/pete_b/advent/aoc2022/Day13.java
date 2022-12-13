package uk.co.pete_b.advent.aoc2022;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
public class Day13 {
    public static Answer sumCorrectOrderIndices(final List<String> pairs) {
        int index = 1;
        int sumValidIndices = 0;

        final List dividerOne = parsePacket("[[2]]");
        final List dividerTwo = parsePacket("[[6]]");
        final List<List> packets = new ArrayList<>();
        packets.add(dividerOne);
        packets.add(dividerTwo);

        for (int i = 0; i < pairs.size(); i++) {
            final List packetOne = parsePacket(pairs.get(i++));
            final List packetTwo = parsePacket(pairs.get(i++));

            if (arePacketsInCorrectOrder(packetOne, packetTwo) == -1) {
                sumValidIndices += index;
            }

            index++;
            packets.add(packetOne);
            packets.add(packetTwo);
        }

        packets.sort(Day13::arePacketsInCorrectOrder);

        final int decoderKey = (packets.indexOf(dividerOne) + 1) * (packets.indexOf(dividerTwo) + 1);

        return new Answer(sumValidIndices, decoderKey);
    }

    private static int arePacketsInCorrectOrder(final List packetOne, final List packetTwo) {
        int valueToCompare = 0;

        while (true) {
            if ((packetOne.size() == packetTwo.size()) && (packetOne.isEmpty() || packetOne.size() <= valueToCompare)) {
                return 0;
            } else {
                if (packetOne.size() <= valueToCompare) {
                    return -1;
                }

                if (packetTwo.size() <= valueToCompare) {
                    return 1;
                }
            }

            final Object leftValueObj = packetOne.get(valueToCompare);
            final Object rightValueObj = packetTwo.get(valueToCompare);

            if (leftValueObj instanceof Integer && rightValueObj instanceof Integer) {
                int left = (int) leftValueObj;
                int right = (int) rightValueObj;

                if (left < right) {
                    return -1;
                } else if (left > right) {
                    return 1;
                }
            } else if (leftValueObj instanceof List && rightValueObj instanceof List) {
                final int comparison = arePacketsInCorrectOrder((List) leftValueObj, (List) rightValueObj);
                if (comparison != 0) {
                    return comparison;
                }
            } else if (leftValueObj instanceof List && rightValueObj instanceof Integer) {
                final List newList = new ArrayList();
                newList.add(rightValueObj);

                final int comparison = arePacketsInCorrectOrder((List) leftValueObj, newList);
                if (comparison != 0) {
                    return comparison;
                }
            } else if (leftValueObj instanceof Integer && rightValueObj instanceof List) {
                final List newList = new ArrayList();
                newList.add(leftValueObj);

                final int comparison = arePacketsInCorrectOrder(newList, (List) rightValueObj);
                if (comparison != 0) {
                    return comparison;
                }
            }

            valueToCompare++;
        }
    }

    private static List parsePacket(final String packetString) {
        final List packet = new ArrayList<>();
        parsePacket(0, packetString.substring(1, packetString.length() - 1), packet);

        return packet;
    }

    private static int parsePacket(final int stringIndex, final String inputString, final List packet) {
        StringBuilder buffer = new StringBuilder();

        for (int i = stringIndex; i < inputString.length(); i++) {
            char character = inputString.charAt(i);
            if (character == '[') {
                final List newPacket = new ArrayList<>();
                packet.add(newPacket);
                i = parsePacket(i + 1, inputString, newPacket);
            } else if (character == ']') {
                if (!buffer.isEmpty()) {
                    packet.add(Integer.valueOf(buffer.toString()));
                }

                return i;
            } else if (character == ',') {
                if (!buffer.isEmpty()) {
                    packet.add(Integer.valueOf(buffer.toString()));
                }
                buffer = new StringBuilder();
            } else {
                buffer.append(character);
            }
        }
        if (!buffer.isEmpty()) {
            packet.add(Integer.valueOf(buffer.toString()));
        }

        return inputString.length();
    }

    public record Answer(int sumOfValidIndices, int decoderKey) {

    }
}
