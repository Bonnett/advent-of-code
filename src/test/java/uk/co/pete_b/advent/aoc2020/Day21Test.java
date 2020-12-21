package uk.co.pete_b.advent.aoc2020;


import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("2020")
public class Day21Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day21Test.class);

    private static final String EXAMPLE_INPUT = """
            mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
            trh fvjkl sbzzf mxmxvkd (contains dairy)
            sqjhc fvjkl (contains soy)
            sqjhc mxmxvkd sbzzf (contains fish)
            """;

    @Test
    public void testExample() {
        assertEquals(new Day21.Answer(5L, "mxmxvkd,sqjhc,fvjkl"), Day21.countAllergenAbsentIngredients(EXAMPLE_INPUT));
    }

    @Test
    public void getAnswers() throws IOException {
        final String inputData = IOUtils.toString(getClass().getResourceAsStream("/puzzle-data/2020/day21"), Charset.defaultCharset());

        LOGGER.info("{}", Day21.countAllergenAbsentIngredients(inputData));
    }
}
