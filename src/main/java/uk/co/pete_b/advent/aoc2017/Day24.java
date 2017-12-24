package uk.co.pete_b.advent.aoc2017;

import java.util.*;

public class Day24 {
    public static class Answers {
        private final int strongestBridge;
        private final int longestBridgeStrength;

        Answers(int strongestBridge, int longestBridgeStrength) {
            this.strongestBridge = strongestBridge;
            this.longestBridgeStrength = longestBridgeStrength;
        }

        public int getStrongestBridge() {
            return strongestBridge;
        }

        public int getLongestBridgeStrength() {
            return longestBridgeStrength;
        }
    }

    public static Answers getBridgeFacts(final String input) {
        final String[] elements = input.split("\r?\n");
        final Map<Integer, Set<BridgeElement>> bridgeLengths = new HashMap<>();
        for (String element : elements) {
            BridgeElement piece = new BridgeElement(element);
            if (!bridgeLengths.containsKey(piece.pieceOne)) {
                bridgeLengths.put(piece.pieceOne, new HashSet<>());
            }
            if (!bridgeLengths.containsKey(piece.pieceTwo)) {
                bridgeLengths.put(piece.pieceTwo, new HashSet<>());
            }
            bridgeLengths.get(piece.pieceOne).add(piece);
            bridgeLengths.get(piece.pieceTwo).add(piece);
        }

        final List<Bridge> possibleBridges = new ArrayList<>();
        final Set<BridgeElement> startElements = bridgeLengths.get(0);
        for (BridgeElement el : startElements) {
            Bridge bridge = new Bridge(el);
            possibleBridges.add(bridge);
        }

        Bridge strongest = new Bridge();
        Bridge longest = new Bridge();

        while (true) {
            final List<Bridge> newBridges = new ArrayList<>();
            final Iterator<Bridge> bridgeIter = possibleBridges.iterator();
            while (bridgeIter.hasNext()) {
                final Bridge bridge = bridgeIter.next();
                final Set<BridgeElement> pieces = bridgeLengths.get(bridge.lastPortValue);
                for (BridgeElement piece : pieces) {
                    final Bridge newBridge = new Bridge(bridge);
                    if (newBridge.addElement(piece)) {
                        newBridges.add(newBridge);

                        if (newBridge.getStrength() > strongest.getStrength()) {
                            strongest = newBridge;
                        }

                        if (newBridge.getLength() > longest.getLength()) {
                            longest = newBridge;
                        } else if (newBridge.getLength() == longest.getLength() && newBridge.getStrength() > longest.getStrength()) {
                            longest = newBridge;
                        }
                    }
                }

                bridgeIter.remove();
            }

            if (newBridges.isEmpty()) {
                break;
            }

            possibleBridges.addAll(newBridges);
        }

        return new Answers(strongest.getStrength(), longest.getStrength());
    }

    private static class BridgeElement {
        private final int pieceOne;
        private final int pieceTwo;
        private final String element;

        BridgeElement(final String element) {
            this.element = element;
            final String[] pieces = element.split("/");
            pieceOne = Integer.valueOf(pieces[0]);
            pieceTwo = Integer.valueOf(pieces[1]);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BridgeElement that = (BridgeElement) o;
            return pieceOne == that.pieceOne &&
                    pieceTwo == that.pieceTwo;
        }

        @Override
        public int hashCode() {
            return Objects.hash(pieceOne, pieceTwo);
        }

        @Override
        public String toString() {
            return element;
        }

        public int getStrength() {
            return pieceOne + pieceTwo;
        }
    }

    private static class Bridge {
        private int lastPortValue = 0;
        private int strength = 0;
        Set<BridgeElement> bridge = new LinkedHashSet<>();

        Bridge() {

        }

        Bridge(BridgeElement startElement) {
            addElement(startElement);
        }

        Bridge(Bridge bridge) {
            this.bridge = new LinkedHashSet<>(bridge.bridge);
            this.lastPortValue = bridge.lastPortValue;
            this.strength = bridge.strength;
        }


        boolean addElement(BridgeElement elementToAdd) {
            if (bridge.contains(elementToAdd)) {
                return false;
            }
            bridge.add(elementToAdd);
            strength += elementToAdd.getStrength();

            if (elementToAdd.pieceOne == lastPortValue) {
                lastPortValue = elementToAdd.pieceTwo;
            } else {
                lastPortValue = elementToAdd.pieceOne;
            }

            return true;
        }

        int getStrength() {
            return strength;
        }

        int getLength() {
            return bridge.size();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (BridgeElement el : bridge) {
                sb.append(el.toString());
                sb.append("-");
            }

            return sb.deleteCharAt(sb.length() - 1).toString();
        }
    }
}
