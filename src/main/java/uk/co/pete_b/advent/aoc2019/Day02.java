package uk.co.pete_b.advent.aoc2019;

public class Day02 {

    public static int[] calculateOpCodes(final int[] input)
    {
        final int[] state = input.clone();
        int currentPos = 0;
        while(currentPos < state.length - 1)
        {
            int opcode = state[currentPos];
            if (opcode == 99)
            {
                break;
            }
            else if (opcode == 1)
            {
                state[state[currentPos + 3]] = state[state[currentPos + 1]] + state[state[currentPos + 2]];
            }
            else if (opcode == 2)
            {
                state[state[currentPos + 3]] = state[state[currentPos + 1]] * state[state[currentPos + 2]];
            }
            else
            {
                throw new IllegalStateException("Something's wrong");
            }
            currentPos += 4;
        }

        return state;
    }
}
