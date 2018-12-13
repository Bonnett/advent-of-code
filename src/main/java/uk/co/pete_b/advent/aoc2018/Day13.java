package uk.co.pete_b.advent.aoc2018;

import uk.co.pete_b.advent.utils.Coordinate;
import uk.co.pete_b.advent.utils.Direction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class Day13 {
    public static Coordinate getFirstCrashCoordinates(final List<String> input) {
        final MineRailway railway = new MineRailway(input);

        Coordinate firstCrash = null;
        while (firstCrash == null) {
            firstCrash = railway.moveCarts(true);
        }

        return firstCrash;
    }

    public static Coordinate getLastRemainingCartCoordinates(final List<String> input) {
        final MineRailway railway = new MineRailway(input);

        Coordinate lastRemaningCart = null;
        while (lastRemaningCart == null) {
            lastRemaningCart = railway.moveCarts(false);
        }

        return lastRemaningCart;
    }

    private static class MineRailway {
        private final List<MineCart> mineCarts = new ArrayList<>();
        private final MineTrack[][] grid;

        public MineRailway(final List<String> inputGrid) {
            grid = new MineTrack[inputGrid.size()][];
            for (int y = 0; y < grid.length; y++) {
                final String row = inputGrid.get(y);
                grid[y] = new MineTrack[row.length()];
                for (int x = 0; x < grid[y].length; x++) {
                    final char piece = row.charAt(x);
                    final Coordinate coordinate = new Coordinate(x, y);
                    if (piece == '/' || piece == '\\') {
                        grid[y][x] = new MineTrack(coordinate, MineTrackType.CURVE);
                    } else if (piece == '-' || piece == '<' || piece == '>') {
                        final MineTrack track;
                        if (piece == '<') {
                            final MineCart cart = new MineCart(coordinate, Direction.LEFT);
                            track = new MineTrack(coordinate, MineTrackType.HORIZONTAL, cart);
                            mineCarts.add(cart);
                        } else if (piece == '>') {
                            final MineCart cart = new MineCart(coordinate, Direction.RIGHT);
                            track = new MineTrack(coordinate, MineTrackType.HORIZONTAL, cart);
                            mineCarts.add(cart);
                        } else {
                            track = new MineTrack(coordinate, MineTrackType.HORIZONTAL);
                        }
                        grid[y][x] = track;
                    } else if (piece == '|' || piece == 'v' || piece == '^') {
                        final MineTrack track;
                        if (piece == 'v') {
                            final MineCart cart = new MineCart(coordinate, Direction.DOWN);
                            track = new MineTrack(coordinate, MineTrackType.VERTICAL, cart);
                            mineCarts.add(cart);
                        } else if (piece == '^') {
                            final MineCart cart = new MineCart(coordinate, Direction.UP);
                            track = new MineTrack(coordinate, MineTrackType.VERTICAL, cart);
                            mineCarts.add(cart);
                        } else {
                            track = new MineTrack(coordinate, MineTrackType.VERTICAL);
                        }
                        grid[y][x] = track;
                    } else if (piece == '+') {
                        grid[y][x] = new MineTrack(coordinate, MineTrackType.INTERSECTION);
                    }
                }
            }
        }

        public Coordinate moveCarts(boolean haltOnExit) {
            final Iterator<MineCart> iterator = mineCarts.iterator();
            while (iterator.hasNext()) {
                final MineCart cart = iterator.next();
                if (cart.hasCrashed()) {
                    iterator.remove();
                }
                cart.resetMoveFlag();
            }

            if (mineCarts.size() == 1) {
                return mineCarts.get(0).getCurrentPosition();
            }

            for (final MineTrack[] mineTracks : grid) {
                for (final MineTrack track : mineTracks) {
                    if (track != null) {
                        final MineCart cart = track.getCurrentCart();
                        if (cart != null && !cart.hasMovedThisTurn() && !cart.hasCrashed()) {
                            if (!moveCart(grid, track, cart) && haltOnExit) {
                                return cart.getCurrentPosition();
                            }
                        }
                    }
                }
            }

            return null;
        }

        private boolean moveCart(final MineTrack[][] grid, final MineTrack currentTrack, final MineCart cart) {
            Coordinate newPosition = null;
            Direction newDirection = null;

            switch (currentTrack.getTrackType()) {

                case HORIZONTAL:
                    if (cart.getCurrentHeading() == Direction.LEFT) {
                        newPosition = currentTrack.getTrackPosition().left();
                    } else if (cart.getCurrentHeading() == Direction.RIGHT) {
                        newPosition = currentTrack.getTrackPosition().right();
                    }
                    newDirection = cart.getCurrentHeading();
                    break;
                case VERTICAL:
                    if (cart.getCurrentHeading() == Direction.UP) {
                        newPosition = currentTrack.getTrackPosition().up();
                    } else if (cart.getCurrentHeading() == Direction.DOWN) {
                        newPosition = currentTrack.getTrackPosition().down();
                    }
                    newDirection = cart.getCurrentHeading();
                    break;
                case CURVE:
                    final Coordinate currentTrackPosition = currentTrack.getTrackPosition();
                    if (cart.getCurrentHeading() == Direction.UP || cart.getCurrentHeading() == Direction.DOWN) {
                        if (currentTrackPosition.getX() - 1 >= 0 && grid[currentTrackPosition.getY()][currentTrackPosition.getX() - 1] != null &&
                                grid[currentTrackPosition.getY()][currentTrackPosition.getX() - 1].getTrackType() != MineTrackType.VERTICAL &&
                                grid[currentTrackPosition.getY()][currentTrackPosition.getX() - 1].getTrackType() != MineTrackType.CURVE) {
                            newPosition = currentTrack.getTrackPosition().left();
                            newDirection = Direction.LEFT;
                        } else {
                            newPosition = currentTrack.getTrackPosition().right();
                            newDirection = Direction.RIGHT;
                        }
                    } else if (cart.getCurrentHeading() == Direction.LEFT || cart.getCurrentHeading() == Direction.RIGHT) {
                        if (currentTrackPosition.getY() - 1 >= 0 && grid[currentTrackPosition.getY() - 1][currentTrackPosition.getX()] != null &&
                                grid[currentTrackPosition.getY() - 1][currentTrackPosition.getX()].getTrackType() != MineTrackType.HORIZONTAL &&
                                grid[currentTrackPosition.getY() - 1][currentTrackPosition.getX()].getTrackType() != MineTrackType.CURVE) {
                            newPosition = currentTrack.getTrackPosition().up();
                            newDirection = Direction.UP;
                        } else {
                            newPosition = currentTrack.getTrackPosition().down();
                            newDirection = Direction.DOWN;
                        }
                    }
                    break;
                case INTERSECTION:
                    final MineCartDirectionChange directionChange = cart.getNextDirectionChange();

                    switch (cart.getCurrentHeading()) {
                        case LEFT:
                            switch (directionChange) {

                                case LEFT:
                                    newPosition = currentTrack.getTrackPosition().down();
                                    newDirection = Direction.DOWN;
                                    break;
                                case STRAIGHT:
                                    newPosition = currentTrack.getTrackPosition().left();
                                    newDirection = cart.getCurrentHeading();
                                    break;
                                case RIGHT:
                                    newPosition = currentTrack.getTrackPosition().up();
                                    newDirection = Direction.UP;
                                    break;
                            }
                            break;
                        case UP:
                            switch (directionChange) {

                                case LEFT:
                                    newPosition = currentTrack.getTrackPosition().left();
                                    newDirection = Direction.LEFT;
                                    break;
                                case STRAIGHT:
                                    newPosition = currentTrack.getTrackPosition().up();
                                    newDirection = cart.getCurrentHeading();
                                    break;
                                case RIGHT:
                                    newPosition = currentTrack.getTrackPosition().right();
                                    newDirection = Direction.RIGHT;
                                    break;
                            }
                            break;
                        case RIGHT:
                            switch (directionChange) {

                                case LEFT:
                                    newPosition = currentTrack.getTrackPosition().up();
                                    newDirection = Direction.UP;
                                    break;
                                case STRAIGHT:
                                    newPosition = currentTrack.getTrackPosition().right();
                                    newDirection = cart.getCurrentHeading();
                                    break;
                                case RIGHT:
                                    newPosition = currentTrack.getTrackPosition().down();
                                    newDirection = Direction.DOWN;
                                    break;
                            }
                            break;
                        case DOWN:
                            switch (directionChange) {

                                case LEFT:
                                    newPosition = currentTrack.getTrackPosition().right();
                                    newDirection = Direction.RIGHT;
                                    break;
                                case STRAIGHT:
                                    newPosition = currentTrack.getTrackPosition().down();
                                    newDirection = cart.getCurrentHeading();
                                    break;
                                case RIGHT:
                                    newPosition = currentTrack.getTrackPosition().left();
                                    newDirection = Direction.LEFT;
                                    break;
                            }
                            break;
                    }
                    cart.changeDirection();
                    break;
            }

            currentTrack.setCart(null);
            boolean validMove = grid[newPosition.getY()][newPosition.getX()].setCart(cart);
            cart.move(newPosition, newDirection);

            return validMove;
        }
    }

    private static class MineTrack {
        private final Coordinate trackPosition;
        private final MineTrackType trackType;
        private MineCart currentCart;

        public MineTrack(final Coordinate trackPosition, final MineTrackType trackType) {
            this.trackPosition = trackPosition;
            this.trackType = trackType;
        }

        public MineTrack(final Coordinate trackPosition, final MineTrackType trackType, final MineCart mineCart) {
            this.trackPosition = trackPosition;
            this.trackType = trackType;
            this.currentCart = mineCart;
        }

        public MineCart getCurrentCart() {
            return this.currentCart;
        }

        public MineTrackType getTrackType() {
            return trackType;
        }

        public boolean setCart(final MineCart newCart) {
            if (this.currentCart == null || newCart == null) {
                this.currentCart = newCart;
                return true;
            } else {
                this.currentCart.markAsCrashed();
                this.currentCart = null;
                newCart.markAsCrashed();
                return false;
            }
        }

        public Coordinate getTrackPosition() {
            return this.trackPosition;
        }
    }

    private static class MineCart {

        private Coordinate currentPosition;
        private MineCartDirectionChange nextDirectionChange;
        private Direction currentHeading;
        private boolean movedThisTurn = false;
        private boolean crashed = false;

        public MineCart(final Coordinate currentPosition, final Direction currentHeading) {
            this.currentPosition = currentPosition;
            this.currentHeading = currentHeading;
            this.nextDirectionChange = MineCartDirectionChange.LEFT;
        }

        public Direction getCurrentHeading() {
            return currentHeading;
        }

        public Coordinate getCurrentPosition() {
            return currentPosition;
        }

        public boolean hasCrashed() {
            return crashed;
        }

        public void move(final Coordinate newPosition, final Direction newDirection) {
            this.movedThisTurn = true;
            this.currentPosition = newPosition;
            this.currentHeading = newDirection;
        }

        public boolean hasMovedThisTurn() {
            return this.movedThisTurn;
        }

        public void resetMoveFlag() {
            this.movedThisTurn = false;
        }

        public MineCartDirectionChange getNextDirectionChange() {
            return this.nextDirectionChange;
        }

        public void changeDirection() {
            this.nextDirectionChange = nextDirectionChange.changeDirection();
        }

        public void markAsCrashed() {
            this.crashed = true;
        }
    }

    private enum MineTrackType {
        HORIZONTAL, VERTICAL, INTERSECTION, CURVE
    }

    private enum MineCartDirectionChange {
        LEFT, STRAIGHT, RIGHT;

        public MineCartDirectionChange changeDirection() {
            if (this == LEFT) {
                return STRAIGHT;
            } else if (this == STRAIGHT) {
                return RIGHT;
            } else {
                return LEFT;
            }
        }
    }
}
