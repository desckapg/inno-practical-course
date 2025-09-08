package by.desckapg.skynet;

import lombok.Getter;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

/**
 * Represents a competing faction that collects robot parts from the factory
 * and can assemble robots from the gathered parts.
 */
public class Faction implements Runnable {

    private static final int TAKING_PART_COUNT_PER_DAY = 5;

    @Getter
    private final String name;
    private final Factory factory;
    private final TimeController controller;
    private final Map<RobotPart, Integer> parts = new EnumMap<>(RobotPart.class);

    /**
     * Creates a faction bound to the given factory.
     *
     * @param name    faction name
     * @param factory shared factory to take parts from
     * @param controller time controller to switch between day and night
     */
    public Faction(String name, Factory factory, TimeController controller) {
        this.name = name;
        this.factory = factory;
        this.controller = controller;
        this.controller.register();
    }

    /**
     * Attempts to take up to a fixed number of parts from the factory for the current day.
     * May wait changing day phase.
     */

    public void run() {
        while (controller.isRunning()) {
            controller.waitNight();
            for (int i = 0; i < TAKING_PART_COUNT_PER_DAY; i++) {
                factory.takePart().ifPresent(part ->
                        parts.put(part, parts.getOrDefault(part, 0) + 1));
            }
            controller.markNightEnd();

        }
    }

    /**
     * Calculates how many complete robots can be assembled
     * based on the minimum count among all required parts.
     *
     * @return number of fully buildable robots
     */
    public int getRobotsCount() {
        return Arrays.stream(RobotPart.values())
                .mapToInt(part -> parts.getOrDefault(part, 0))
                .min()
                .orElse(0);
    }

}
