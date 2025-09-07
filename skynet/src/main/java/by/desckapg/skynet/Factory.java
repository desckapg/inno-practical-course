package by.desckapg.skynet;

import java.util.Optional;
import java.util.Random;
import java.util.Stack;

/**
 * by.desckapg.skynet.Factory that randomly produces robot parts and allows synchronized retrieval of parts.
 * Internally stores parts in a LIFO stack.
 */
public class Factory {

    private static final int MAX_PRODUCED_PART_PER_DAY = 10;
    private final Random random;
    private final Stack<RobotPart> parts;

    /**
     * Creates a new factory with its own random source and empty storage.
     */
    public Factory() {
        this.random = new Random();
        this.parts = new Stack<>();
    }

    /**
     * Produces a random number of parts for the current day (0..MAX inclusive)
     * and pushes them onto the internal storage.
     */
    public void produceParts() {
        int partsCount = random.nextInt(MAX_PRODUCED_PART_PER_DAY + 1);
        for (; partsCount > 0; partsCount--) {
            int partOrdinal = random.nextInt(RobotPart.values().length);
            parts.push(RobotPart.values()[partOrdinal]);
        }
    }

    /**
     * Retrieves a part from the factory storage in a thread-safe manner.
     *
     * @return a part if available; otherwise empty
     */
    public synchronized Optional<RobotPart> takePart() {
        if (parts.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(parts.pop());
    }


}
