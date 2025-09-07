package by.desckapg.skynet;

import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

/**
 * Represents a competing faction that collects robot parts from the factory
 * and can assemble robots from the gathered parts.
 */
public class Faction {

    private static final int TAKING_PART_COUNT_PER_DAY = 5;

    private final String name;
    private final Factory factory;
    private final Map<RobotPart, Integer> parts;
    private final Random random;

    /**
     * Creates a faction bound to the given factory.
     *
     * @param name    faction name
     * @param factory shared factory to take parts from
     */
    public Faction(String name, Factory factory) {
        this.name = name;
        this.factory = factory;
        this.parts = new EnumMap<>(RobotPart.class);
        this.random = new Random();
    }

    /**
     * Attempts to take up to a fixed number of parts from the factory for the current day.
     * May sleep briefly between attempts to reduce bias when called concurrently.
     */
    public void startTakingParts() {
        for (int i = 0; i < TAKING_PART_COUNT_PER_DAY; i++) {
            factory.takePart().ifPresent(part ->
                    parts.put(part, parts.getOrDefault(part, 0) + 1));
            // To prevent predictable first-called by.desckapg.skynet.Faction as a winner
            try {
                Thread.sleep(random.nextInt(0, 20));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Calculates how many complete robots can be assembled
     * based on the minimum count among all required parts.
     *
     * @return number of fully buildable robots
     */
    public int getRobotsCount() {
        return parts.values().stream()
                .mapToInt(Integer::intValue)
                .min()
                .orElse(0);
    }

    /**
     * Returns the faction name.
     *
     * @return faction name
     */
    public String getName() {
        return name;
    }
}
