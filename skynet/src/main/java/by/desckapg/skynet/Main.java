package by.desckapg.skynet;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Entry point that simulates a multi-day competition between two factions
 * collecting parts from a shared factory using a thread pool.
 */
public class Main {

    private static final int DAYS_COUNT = 100;

    /**
     * Runs the simulation for a fixed number of days:
     * each day the factory produces parts and both factions attempt to take them.
     * Finally prints the winner based on the number of buildable robots.
     *
     */
    public static void main(String[] args) {
        var pool = Executors.newCachedThreadPool();
        var controller = new TimeController(100);
        var factory = new Factory(controller);
        var world = new Faction("World", factory, controller);
        var wednesday = new Faction("Wednesday", factory, controller);
        pool.execute(factory);
        pool.execute(wednesday);
        pool.execute(world);
        controller.awaitEnd(3, TimeUnit.SECONDS);
        pool.shutdown();
        try {
            if (!pool.awaitTermination(3, TimeUnit.SECONDS)) {
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String winner = world.getRobotsCount() > wednesday.getRobotsCount() ?
                world.getName() : wednesday.getName();
        System.out.printf("The winner is %s (%d %s's robots vs %d %s's robots)\n",
                winner,
                world.getRobotsCount(), world.getName(),
                wednesday.getRobotsCount(), wednesday.getName()
        );
    }

}
