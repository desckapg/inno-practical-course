package by.desckapg.skynet;

import lombok.Getter;

import java.util.Optional;
import java.util.Random;
import java.util.Stack;

/**
 * Factory that randomly produces robot parts and allows synchronized retrieval of parts.
 * Internally stores parts in a LIFO stack.
 */
public class Factory implements Runnable {

    private static final int MAX_PRODUCED_PART_PER_DAY = 10;

    private final TimeController timeController;
    private final Random random = new Random();
    private final Stack<RobotPart> parts = new Stack<>();

    /**
     * Creates a new factory with external time controller.
     */
    public Factory(TimeController timeController) {
        this.timeController = timeController;
        timeController.register();
    }

    /**
     * Produces a random number of parts for the current day (0..MAX inclusive)
     * and pushes them onto the internal storage. May wait changing day phase
     */
    @Override
    public void run() {
        while (timeController.isRunning()) {
            int partsCount = random.nextInt(MAX_PRODUCED_PART_PER_DAY + 1);
            for (; partsCount > 0; partsCount--) {
                int partOrdinal = random.nextInt(RobotPart.values().length);
                synchronized (parts) {
                    parts.push(RobotPart.values()[partOrdinal]);
                }
            }
            timeController.markDayEnd();
            timeController.waitDay();
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
