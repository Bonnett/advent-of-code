package uk.co.pete_b.advent.aoc2019;

public class Day01 {
    public static int calculateFuelRequired(final int mass)
    {
        return (int) (Math.floor((double) mass / 3)) - 2;
    }

    public static int calculateFuelRequiredWithFuelMass(int mass) {
        int totalFuel = calculateFuelRequired(mass);
        int additionalFuel = totalFuel;
        while (true)
        {
            int extraFuel = calculateFuelRequired(additionalFuel);
            if (extraFuel <= 0)
            {
                break;
            }
            totalFuel += extraFuel;
            additionalFuel = extraFuel;
        }

        return totalFuel;
    }
}
