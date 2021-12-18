package uk.co.pete_b.advent.aoc2021;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day18 {
    public static List<Token> reduceSums(final List<String> sums) {
        final List<List<Token>> parsedSums = parseSums(sums);

        if (parsedSums.size() > 1) {
            return parsedSums.stream().reduce(Day18::sumTokens).orElseThrow();
        } else {
            return reduceSum(parsedSums.get(0));
        }
    }

    public static int calculateMagnitude(final List<String> sums) {
        final List<Token> reduced = reduceSums(sums);

        return calculateReducedMagnitude(reduced);
    }

    public static int calculateMaximumMagnitudeFromAnyTwo(final List<String> sums) {
        final List<List<Token>> parsedSums = parseSums(sums);

        int maxMagnitude = 0;

        for (int i = 0; i < parsedSums.size(); i++) {
            for (int j = 0; j < parsedSums.size(); j++) {
                if (i == j) {
                    continue;
                }
                final List<Token> listOne = parsedSums.get(i).stream().map(Token::new).collect(Collectors.toList());
                final List<Token> listTwo = parsedSums.get(j).stream().map(Token::new).collect(Collectors.toList());
                maxMagnitude = Math.max(maxMagnitude, calculateReducedMagnitude(sumTokens(listOne, listTwo)));
            }
        }

        return maxMagnitude;
    }

    private static List<List<Token>> parseSums(List<String> sums) {
        final List<List<Token>> parsedSums = new ArrayList<>();
        for (String sum : sums) {
            final List<Token> parsedSum = new ArrayList<>();
            for (int i = 0; i < sum.length(); i++) {
                final String token = sum.substring(i, i + 1);
                switch (token) {
                    case "[" -> parsedSum.add(new Token(TokenType.OPEN_PAIR));
                    case "]" -> parsedSum.add(new Token(TokenType.CLOSE_PAIR));
                    case "," -> parsedSum.add(new Token(TokenType.SEPARATOR));
                    default -> parsedSum.add(new Token(Integer.parseInt(token)));
                }
            }

            parsedSums.add(parsedSum);
        }
        return parsedSums;
    }

    private static int calculateReducedMagnitude(final List<Token> reduced) {
        do {
            final List<Token> working = new ArrayList<>();
            int startPos = 0;
            while (startPos < reduced.size()) {
                if (reduced.get(startPos).tokenType == TokenType.OPEN_PAIR && reduced.get(startPos + 4).tokenType == TokenType.CLOSE_PAIR) {
                    working.add(new Token(reduced.get(startPos + 1).value * 3 + reduced.get(startPos + 3).value * 2));
                    working.addAll(reduced.subList(startPos + 5, reduced.size()));
                    reduced.clear();
                    reduced.addAll(working);
                    break;
                } else {
                    working.add(reduced.get(startPos));
                }
                startPos++;
            }

        } while (reduced.size() != 1);

        return reduced.get(0).value;
    }

    private static List<Token> sumTokens(List<Token> tokenOne, List<Token> tokenTwo) {
        final List<Token> sum = new ArrayList<>();
        sum.add(new Token(TokenType.OPEN_PAIR));
        sum.addAll(tokenOne);
        sum.add(new Token(TokenType.SEPARATOR));
        sum.addAll(tokenTwo);
        sum.add(new Token(TokenType.CLOSE_PAIR));

        return reduceSum(sum);
    }

    private static List<Token> reduceSum(final List<Token> sum) {
        while (true) {
            boolean hasExploded = performExplosions(sum);
            boolean hasSplit = performSplits(sum);
            if (!hasExploded && !hasSplit) {
                break;
            }
        }

        return sum;
    }

    private static boolean performExplosions(final List<Token> sum) {
        boolean hasExploded = false;

        int lastNumberFound = -1;
        int curDepth = 0;
        int position = 0;
        while (position < sum.size()) {
            boolean explode = false;
            final Token token = sum.get(position);
            if (token.tokenType == TokenType.OPEN_PAIR) {
                curDepth++;
                if (curDepth == 5) {
                    explode = true;
                    hasExploded = true;
                }
            } else if (token.tokenType == TokenType.CLOSE_PAIR) {
                curDepth--;
            } else if (token.tokenType == TokenType.NUMBER) {
                lastNumberFound = position;
            }

            if (explode) {
                sum.remove(position + 4);
                final Token right = sum.remove(position + 3);
                sum.remove(position + 2);
                final Token left = sum.remove(position + 1);
                sum.remove(position);
                sum.add(position, new Token(0));
                if (lastNumberFound != -1) {
                    sum.get(lastNumberFound).value += left.value;
                }

                final List<Token> remainingList = sum.subList(position + 1, sum.size());
                for (Token remaining : remainingList) {
                    if (remaining.tokenType == TokenType.NUMBER) {
                        remaining.value += right.value;
                        break;
                    }
                }

                lastNumberFound = position;
                position++;
                curDepth--;
            } else {
                position++;
            }
        }

        return hasExploded;
    }

    private static boolean performSplits(final List<Token> sum) {
        boolean hasSplit = false;

        int position = 0;
        while (position < sum.size()) {
            final Token token = sum.get(position);
            if (token.tokenType == TokenType.NUMBER) {
                if (token.value > 9) {
                    hasSplit = true;
                }
            }

            if (hasSplit) {
                int left;
                int right;
                if (token.value % 2 == 0) {
                    left = right = token.value / 2;
                } else {
                    left = (token.value - 1) / 2;
                    right = left + 1;
                }
                final List<Token> workingSum = new ArrayList<>();
                workingSum.add(new Token(TokenType.OPEN_PAIR));
                workingSum.add(new Token(left));
                workingSum.add(new Token(TokenType.SEPARATOR));
                workingSum.add(new Token(right));
                workingSum.add(new Token(TokenType.CLOSE_PAIR));
                sum.remove(position);
                sum.addAll(position, workingSum);
                break;
            } else {
                position++;
            }
        }

        return hasSplit;
    }

    private enum TokenType {OPEN_PAIR, NUMBER, SEPARATOR, CLOSE_PAIR}

    private static class Token {
        private final TokenType tokenType;
        private int value = -1;

        public Token(TokenType tokenType) {
            this.tokenType = tokenType;
        }

        public Token(final int value) {
            this.value = value;
            this.tokenType = TokenType.NUMBER;
        }

        public Token(Token token) {
            this.value = token.value;
            this.tokenType = token.tokenType;
        }

        @Override
        public String toString() {
            switch (tokenType) {
                case OPEN_PAIR -> {
                    return "[";
                }
                case NUMBER -> {
                    return String.valueOf(value);
                }
                case SEPARATOR -> {
                    return ",";
                }
                case CLOSE_PAIR -> {
                    return "]";
                }
            }

            return "";
        }
    }
}
