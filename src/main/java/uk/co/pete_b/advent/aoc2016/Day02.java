package uk.co.pete_b.advent.aoc2016;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;

public class Day02 {

    public static final Button part1StartButtons() {
        final Button number1 = new Button("1");
        final Button number2 = new Button("2");
        final Button number3 = new Button("3");
        final Button number4 = new Button("4");
        final Button number5 = new Button("5");
        final Button number6 = new Button("6");
        final Button number7 = new Button("7");
        final Button number8 = new Button("8");
        final Button number9 = new Button("9");

        // Initialise the numbers
        number1.setRight(number2);
        number1.setDown(number4);
        number2.setRight(number3);
        number2.setDown(number5);
        number3.setDown(number6);
        number4.setRight(number5);
        number4.setDown(number7);
        number5.setRight(number6);
        number5.setDown(number8);
        number6.setDown(number9);
        number7.setRight(number8);
        number8.setRight(number9);

        return number5;
    }

    public static final Button part2StartButtons() {
        final Button button1 = new Button("1");
        final Button button2 = new Button("2");
        final Button button3 = new Button("3");
        final Button button4 = new Button("4");
        final Button button5 = new Button("5");
        final Button button6 = new Button("6");
        final Button button7 = new Button("7");
        final Button button8 = new Button("8");
        final Button button9 = new Button("9");
        final Button buttonA = new Button("A");
        final Button buttonB = new Button("B");
        final Button buttonC = new Button("C");
        final Button buttonD = new Button("D");

        // Initialise the buttons
        button1.setDown(button3);
        button2.setRight(button3);
        button2.setDown(button6);
        button3.setRight(button4);
        button3.setDown(button7);
        button4.setDown(button8);
        button5.setRight(button6);
        button6.setRight(button7);
        button6.setDown(buttonA);
        button7.setRight(button8);
        button7.setDown(buttonB);
        button8.setRight(button9);
        button8.setDown(buttonC);
        buttonA.setRight(buttonB);
        buttonB.setRight(buttonC);
        buttonB.setDown(buttonD);

        return button5;
    }

    private Button startButton;

    public Day02(Button startButton) {
        this.startButton = startButton;
    }

    public String calculateCombination(String combination) {
        Button button = startButton;
        List<String> keyPresses = new ArrayList<>();
        String[] lines = StringUtils.split(combination);

        for (String line : lines) {
            for (int i = 0; i < line.length(); i++) {
                switch (line.charAt(i)) {
                    case 'U':
                        button = button.goUp();
                        break;
                    case 'D':
                        button = button.goDown();
                        break;
                    case 'L':
                        button = button.goLeft();
                        break;
                    case 'R':
                        button = button.goRight();
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            }

            keyPresses.add(button.getKey());
        }

        return StringUtils.join(keyPresses.toArray(new String[0]));
    }

    private static class Button {
        private Button up = this;
        private Button right = this;
        private Button left = this;
        private Button down = this;

        private final String key;

        public Button(String key) {
            this.key = key;
        }

        public Button goUp() {
            return up;
        }

        public void setUp(Button up) {
            if (this.up == this) {
                this.up = up;
                up.setDown(this);
            }
        }

        public Button goRight() {
            return right;
        }

        public void setRight(Button right) {
            if (this.right == this) {
                this.right = right;
                right.setLeft(this);
            }
        }

        public Button goLeft() {
            return left;
        }

        public void setLeft(Button left) {
            if (this.left == this) {
                this.left = left;
                left.setRight(this);
            }
        }

        public Button goDown() {
            return down;
        }

        public void setDown(Button down) {
            if (this.down == this) {
                this.down = down;
                down.setUp(this);
            }
        }

        public String getKey() {
            return key;
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this, "left", "right", "up", "down");
        }

        @Override
        public boolean equals(Object otherCoords) {
            return EqualsBuilder.reflectionEquals(this, otherCoords, "left", "right", "up", "down");
        }

    }
}
