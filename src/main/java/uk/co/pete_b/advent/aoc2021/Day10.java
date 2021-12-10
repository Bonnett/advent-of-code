package uk.co.pete_b.advent.aoc2021;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.*;

public class Day10 {

    private static final Set<Character> OPENING_CHARS = Set.of('(', '[', '{', '<');
    private static final Map<Character, Character> CHAR_PAIRS = Map.of(')', '(', ']', '[', '}', '{', '>', '<');

    private static final Map<Character, Integer> ERROR_SCORES = Map.of(')', 3, ']', 57, '}', 1197, '>', 25137);
    private static final Map<Character, Integer> FIX_SCORES = Map.of('(', 1, '[', 2, '{', 3, '<', 4);

    public static Answer calculateSyntaxScore(final List<String> navigationLines) {
        int syntaxErrorScore = 0;
        final List<Long> correctionScores = new ArrayList<>();
        for (String line : navigationLines) {
            final Deque<Character> bracketOrder = new ArrayDeque<>();
            boolean incomplete = true;
            for (int i = 0; i < line.length() && incomplete; i++) {
                char bracket = line.charAt(i);
                if (OPENING_CHARS.contains(bracket)) {
                    bracketOrder.add(bracket);
                } else if (bracketOrder.isEmpty()) {
                    syntaxErrorScore += ERROR_SCORES.get(bracket);
                    incomplete = false;
                } else if (CHAR_PAIRS.get(bracket).equals(bracketOrder.peekLast())) {
                    bracketOrder.removeLast();
                } else {
                    syntaxErrorScore += ERROR_SCORES.get(bracket);
                    incomplete = false;
                }
            }

            if (incomplete) {
                long correctionScore = 0;
                while (!bracketOrder.isEmpty()) {
                    correctionScore *= 5;
                    correctionScore += FIX_SCORES.get(bracketOrder.removeLast());
                }
                correctionScores.add(correctionScore);
            }
        }

        correctionScores.sort(Long::compareTo);

        return new Answer(syntaxErrorScore, correctionScores.get(correctionScores.size() / 2));
    }

    public static class Answer {
        private final int syntaxErrorScore;
        private final long middleCompletionScore;

        Answer(int syntaxErrorScore, long middleCorrectionScore) {
            this.syntaxErrorScore = syntaxErrorScore;
            this.middleCompletionScore = middleCorrectionScore;
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this, false);
        }

        @Override
        public boolean equals(final Object otherAnswer) {
            boolean isEqual = false;
            if (otherAnswer instanceof Answer) {
                isEqual = ((Answer) otherAnswer).syntaxErrorScore == this.syntaxErrorScore && ((Answer) otherAnswer).middleCompletionScore == this.middleCompletionScore;
            }

            return isEqual;
        }

        @Override
        public String toString() {
            return String.format("Syntax Error Score %d, Middle Completion Score: %d", syntaxErrorScore, middleCompletionScore);
        }
    }
}
