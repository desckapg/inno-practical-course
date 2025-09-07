package by.desckapg.skynet;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

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
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var pool = Executors.newCachedThreadPool();
        var factory = new Factory();
        var world = new Faction("World", factory);
        var wednesday = new Faction("Wednesday", factory);
        for (int i = 0; i < DAYS_COUNT; i++) {
            pool.submit(factory::produceParts).get();
            var task1 = pool.submit(world::startTakingParts);
            var task2 = pool.submit(wednesday::startTakingParts);
            task1.get();
            task2.get();
        }
        pool.close();
        String winner = world.getRobotsCount() > wednesday.getRobotsCount() ?
                world.getName() : wednesday.getName();
        System.out.printf("The winner is %s (%d %s's robots vs %d %s's robots)\n",
                winner,
                world.getRobotsCount(), world.getName(),
                wednesday.getRobotsCount(), wednesday.getName()
        );
    }

}
