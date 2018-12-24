package uk.co.pete_b.advent.aoc2018;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day24 {
    public static int getRemainingUnits(final List<String> input, boolean useBoost) {
        int total;
        int boost = 0;

        List<Group> groups = new ArrayList<>();
        List<Group> immuneSystem = new ArrayList<>();
        List<Group> infections = new ArrayList<>();
        Side side = null;
        for (String line : input) {
            if (line.startsWith("Immune")) {
                side = Side.IMMUNE;
                continue;
            } else if (line.startsWith("Infection")) {
                side = Side.INFECTION;
                continue;
            } else if (line.isEmpty()) {
                continue;
            }

            final Group group = new Group(side, line);
            groups.add(group);
            if (side == Side.IMMUNE) {
                immuneSystem.add(group);
            } else {
                infections.add(group);
            }
        }

        final List<Group> allGroups = new ArrayList<>(groups);

        while (true) {
            int totalKilled = 0;
            while (!infections.isEmpty() && !immuneSystem.isEmpty()) {
                totalKilled = 0;
                // Sort to find target selection order
                groups.sort((groupOne, groupTwo) -> {
                    final int g1Power = groupOne.getEffectivePower();
                    final int g2Power = groupTwo.getEffectivePower();
                    if (g1Power > g2Power) {
                        return -1;
                    } else if (g1Power < g2Power) {
                        return 1;
                    } else {
                        return Integer.compare(groupTwo.initiative, groupOne.initiative);
                    }
                });


                // Target Selection
                for (final Group group : groups) {
                    if (group.side == Side.INFECTION) {
                        chooseAttacker(immuneSystem, group);
                    } else {
                        chooseAttacker(infections, group);
                    }
                }

                // Sort to find attack order
                groups.sort((groupOne, groupTwo) -> Integer.compare(groupTwo.initiative, groupOne.initiative));

                //Attack!
                for (final Group group : groups) {
                    if (group.numberOfUnits > 0) {
                        totalKilled += group.attack();
                    }
                }

                final Iterator<Group> iterator = groups.iterator();
                while (iterator.hasNext()) {
                    final Group group = iterator.next();
                    group.groupToAttack = null;
                    group.attacker = null;
                    if (group.numberOfUnits <= 0) {
                        iterator.remove();
                        if (group.side == Side.IMMUNE) {
                            immuneSystem.remove(group);
                        } else {
                            infections.remove(group);
                        }
                    }
                }

                if (totalKilled == 0) {
                    break;
                }
            }

            total = (immuneSystem.isEmpty()) ? getTotal(infections) : getTotal(immuneSystem);
            if (totalKilled != 0) {
                if (!useBoost || !immuneSystem.isEmpty()) {
                    break;
                }
            }

            boost++;

            groups = new ArrayList<>();
            immuneSystem = new ArrayList<>();
            infections = new ArrayList<>();
            for (Group group : allGroups) {
                group.reset(boost);
                groups.add(group);
                if (group.side == Side.IMMUNE) {
                    immuneSystem.add(group);
                } else {
                    infections.add(group);
                }
            }

        }

        return total;
    }

    private static int getTotal(final List<Group> victors) {
        return victors.stream().mapToInt(group -> group.numberOfUnits).sum();
    }

    private static void chooseAttacker(final List<Group> enemies, final Group group) {
        Group toAttack = null;
        int attackDamage = 0;
        for (final Group enemy : enemies) {
            if (enemy.attacker != null) {
                continue;
            }
            int damageDealt;
            if (enemy.immunities.contains(group.attackType)) {
                damageDealt = 0;
            } else if (enemy.weaknesses.contains(group.attackType)) {
                damageDealt = 2 * group.getEffectivePower();
            } else {
                damageDealt = group.getEffectivePower();
            }

            if (toAttack == null || damageDealt > attackDamage) {
                toAttack = enemy;
                attackDamage = damageDealt;
            } else if (attackDamage == damageDealt && (toAttack.getEffectivePower() < enemy.getEffectivePower() || (toAttack.getEffectivePower() == enemy.getEffectivePower() && toAttack.initiative < enemy.initiative))) {
                toAttack = enemy;
                attackDamage = damageDealt;
            }
        }

        if (attackDamage > 0) {
            group.setToAttack(toAttack);
        } else {
            group.setToAttack(null);
        }
    }

    public static class Group {

        private static final Pattern UNIT_PATTERN = Pattern.compile("^([0-9]+) units each with ([0-9]+) hit points (\\([^)]+\\) )?with an attack that does ([0-9]+) ([a-zA-Z]+) damage at initiative ([0-9]+)");

        public enum AttackType {
            BLUDGEONING, RADIATION, SLASHING, FIRE, COLD
        }

        private Side side;
        private int startUnits;
        private int numberOfUnits;
        private int attackDamage;
        private int startDamage;
        private int hitPoints;
        private int initiative;
        private AttackType attackType;
        private Set<AttackType> weaknesses;
        private Set<AttackType> immunities;
        private Group groupToAttack = null;
        private Group attacker = null;

        public Group(final Side side, final String input) {
            this.side = side;
            final Matcher matcher = UNIT_PATTERN.matcher(input);
            if (matcher.matches()) {
                this.numberOfUnits = Integer.valueOf(matcher.group(1));
                this.startUnits = this.numberOfUnits;
                this.hitPoints = Integer.valueOf(matcher.group(2));
                if (matcher.group(3) != null) {
                    final String weaknessesAndImmunities = matcher.group(3).substring(1, matcher.group(3).length() - 2);
                    final String[] parts = weaknessesAndImmunities.split("; ");
                    if (parts.length == 2) {
                        this.immunities = new HashSet<>();
                        this.weaknesses = new HashSet<>();

                        if (parts[0].startsWith("immune")) {
                            final String[] immuneStr = parts[0].substring(10).split(", ");
                            for (String part : immuneStr) {
                                this.immunities.add(AttackType.valueOf(part.toUpperCase()));
                            }

                            final String[] weaknessStr = parts[1].substring(8).split(", ");
                            for (String part : weaknessStr) {
                                this.weaknesses.add(AttackType.valueOf(part.toUpperCase()));
                            }
                        } else {
                            final String[] weaknessStr = parts[0].substring(8).split(", ");
                            for (String part : weaknessStr) {
                                this.weaknesses.add(AttackType.valueOf(part.toUpperCase()));
                            }

                            final String[] immuneStr = parts[1].substring(10).split(", ");
                            for (String part : immuneStr) {
                                this.immunities.add(AttackType.valueOf(part.toUpperCase()));
                            }
                        }

                    } else {
                        if (parts[0].startsWith("immune")) {
                            this.weaknesses = Collections.emptySet();
                            this.immunities = new HashSet<>();
                            final String[] immuneStr = weaknessesAndImmunities.substring(10).split(", ");
                            for (String part : immuneStr) {
                                this.immunities.add(AttackType.valueOf(part.toUpperCase()));
                            }
                        } else {
                            this.immunities = Collections.emptySet();
                            this.weaknesses = new HashSet<>();
                            final String[] weaknessStr = weaknessesAndImmunities.substring(8).split(", ");
                            for (String part : weaknessStr) {
                                this.weaknesses.add(AttackType.valueOf(part.toUpperCase()));
                            }
                        }
                    }
                } else {
                    this.weaknesses = Collections.emptySet();
                    this.immunities = Collections.emptySet();
                }
                this.attackDamage = Integer.valueOf(matcher.group(4));
                this.startDamage = this.attackDamage;
                this.attackType = AttackType.valueOf(matcher.group(5).toUpperCase());
                this.initiative = Integer.valueOf(matcher.group(6));
            }
        }

        public void setToAttack(final Group group) {
            if (this.groupToAttack != null) {
                this.groupToAttack.attacker = null;
            }

            this.groupToAttack = group;
            if (group != null) {
                group.beingAttackedBy(this);
            }
        }

        public int attack() {
            int numberKilled = 0;
            if (this.groupToAttack != null) {
                int damage = this.getEffectivePower();
                int multiplier = this.groupToAttack.weaknesses.contains(this.attackType) ? 2 : 1;
                numberKilled = (multiplier * damage) / this.groupToAttack.hitPoints;

                this.groupToAttack.numberOfUnits -= Math.min(this.groupToAttack.numberOfUnits, numberKilled);
            }

            return numberKilled;
        }

        private void beingAttackedBy(final Group group) {
            this.attacker = group;
        }

        public int getEffectivePower() {
            return this.numberOfUnits * this.attackDamage;
        }

        public void reset(final int boostValue) {
            this.numberOfUnits = this.startUnits;
            this.attackDamage = this.startDamage + ((this.side == Side.IMMUNE) ? boostValue : 0);
        }

        @Override
        public String toString() {
            return String.format("%s %d units each with %d hit points (immune to %s; weak to %s) with an attack that does %d %s damage at initiative %d",
                    this.side, this.numberOfUnits, this.hitPoints, this.immunities, this.weaknesses, this.attackDamage, this.attackType, this.initiative);
        }
    }

    public enum Side {
        IMMUNE, INFECTION
    }
}
