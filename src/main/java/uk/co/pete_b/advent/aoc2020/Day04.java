package uk.co.pete_b.advent.aoc2020;

import java.util.Arrays;
import java.util.List;

public class Day04 {

    private static final List<String> FIELDS_REQUIRED = Arrays.asList("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");

    private static final List<Validator> FIELD_VALIDATORS = Arrays.asList(new BirthYearValidator(), new IssueYearValidator(), new ExpirationYearValidator(),
            new HeightValidator(), new HairColorValidator(), new EyeColorValidator(), new PassportIdValidator());

    public static long countValidPassportsCorrectFieldCount(final String input) {
        return Arrays.stream(input.split("\n\n")).filter(Day04::doesPassportContainValidFields).count();
    }

    public static long countValidPassports(final String input) {
        return Arrays.stream(input.split("\n\n")).filter(Day04::doesPassportContainValidFields).filter(Day04::isPassportValid).count();
    }

    private static boolean doesPassportContainValidFields(final String passport) {
        return FIELDS_REQUIRED.stream().filter(passport::contains).count() == FIELDS_REQUIRED.size();
    }

    private static boolean isPassportValid(final String passport) {
        final String tidiedPassport = passport.replaceAll("\n", " ");
        return FIELD_VALIDATORS.stream().filter(validator -> validator.isFieldValid(tidiedPassport)).count() == FIELD_VALIDATORS.size();
    }

    private static abstract class Validator {
        private final String key;

        Validator(final String key) {
            this.key = key;
        }

        abstract boolean isFieldValid(String input);

        protected String getFieldValue(String input) {
            int start = input.indexOf(this.key) + this.key.length() + 1;
            int end = input.substring(start).indexOf(" ");
            if (end == -1) {
                return input.substring(start);
            } else {
                return input.substring(start, start + end);
            }
        }
    }

    private static class BirthYearValidator extends Validator {
        BirthYearValidator() {
            super("byr");
        }

        @Override
        public boolean isFieldValid(String input) {
            final String value = getFieldValue(input);
            return value.matches("[0-9]{4}") && Integer.parseInt(value) >= 1920 && Integer.parseInt(value) <= 2002;
        }
    }

    private static class IssueYearValidator extends Validator {
        IssueYearValidator() {
            super("iyr");
        }

        @Override
        public boolean isFieldValid(String input) {
            final String value = getFieldValue(input);
            return value.matches("[0-9]{4}") && Integer.parseInt(value) >= 2010 && Integer.parseInt(value) <= 2020;
        }
    }

    private static class ExpirationYearValidator extends Validator {
        ExpirationYearValidator() {
            super("eyr");
        }

        @Override
        public boolean isFieldValid(String input) {
            final String value = getFieldValue(input);
            return value.matches("[0-9]{4}") && Integer.parseInt(value) >= 2020 && Integer.parseInt(value) <= 2030;
        }
    }

    private static class HeightValidator extends Validator {
        HeightValidator() {
            super("hgt");
        }

        @Override
        public boolean isFieldValid(String input) {
            final String value = getFieldValue(input);

            if (value.matches("[0-9]{2,3}(cm|in)")) {
                int height = Integer.parseInt(value.substring(0, value.length() - 2));
                if (value.endsWith("cm")) {
                    return height >= 150 && height <= 193;
                } else {
                    return height >= 59 && height <= 76;
                }
            }

            return false;
        }
    }

    private static class HairColorValidator extends Validator {

        HairColorValidator() {
            super("hcl");
        }

        @Override
        public boolean isFieldValid(String input) {
            final String value = getFieldValue(input);

            return value.matches("#[a-f0-9]{6}");
        }
    }

    private static class EyeColorValidator extends Validator {

        EyeColorValidator() {
            super("ecl");
        }

        @Override
        public boolean isFieldValid(String input) {
            final String value = getFieldValue(input);

            return value.matches("amb|blu|brn|gry|grn|hzl|oth");
        }
    }

    private static class PassportIdValidator extends Validator {

        PassportIdValidator() {
            super("pid");
        }

        @Override
        public boolean isFieldValid(String input) {
            final String value = getFieldValue(input);

            return value.matches("[0-9]{9}");
        }
    }
}
