package uk.co.pete_b.advent.aoc2019;

import uk.co.pete_b.advent.utils.Coordinate;

import java.util.*;
import java.util.concurrent.*;

public class Day13 {
    public static long countBlocks(final List<Long> operations) throws Exception {
        final ArcadeCabinet cabinet = runArcadeCabinet(operations);
        return cabinet.getGameTiles().values().stream().filter(x -> x == 2L).count();
    }

    public static long playGame(final List<Long> operations) throws Exception {
        operations.set(0, 2L);
        final ArcadeCabinet cabinet = runArcadeCabinet(operations);
        return cabinet.getCurrentScore();
    }

    private static ArcadeCabinet runArcadeCabinet(final List<Long> operations) throws Exception {
        final ArcadeCabinet cabinet = new ArcadeCabinet();
        final ConsumerSupplierOpCodeComputer computer = new ConsumerSupplierOpCodeComputer(operations, cabinet::moveJoystick, cabinet::drawScreen);
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(computer);
        executor.shutdown();
        executor.awaitTermination(1L, TimeUnit.MINUTES);

        return cabinet;
    }

    private static class ArcadeCabinet {
        private final Queue<Long> inputBuffer = new ArrayDeque<>();
        private final Map<Coordinate, Integer> gameTiles = new ConcurrentHashMap<>();

        private int currentBallPosition = 0;
        private int currentPaddlePosition = 0;
        private long currentScore = 0;

        public Long moveJoystick() {
            return (long) Long.compare(this.currentBallPosition, this.currentPaddlePosition);
        }

        public void drawScreen(final Long input) {
            this.inputBuffer.add(input);
            if (this.inputBuffer.size() == 3) {
                final int xCoord = Objects.requireNonNull(this.inputBuffer.poll()).intValue();
                final int yCoord = Objects.requireNonNull(this.inputBuffer.poll()).intValue();
                final int tileId = Objects.requireNonNull(this.inputBuffer.poll()).intValue();

                if (xCoord == -1 & yCoord == 0) {
                    this.currentScore = tileId;
                } else {
                    if (tileId == 3) {
                        this.currentPaddlePosition = xCoord;
                    } else if (tileId == 4) {
                        this.currentBallPosition = xCoord;
                    }
                    this.gameTiles.put(new Coordinate(xCoord, yCoord), tileId);
                }
            }
        }

        public Map<Coordinate, Integer> getGameTiles() {
            return this.gameTiles;
        }

        public long getCurrentScore() {
            return this.currentScore;
        }
    }
}
