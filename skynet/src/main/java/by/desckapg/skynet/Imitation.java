package by.desckapg.skynet;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Class that imitate a multi-day competition between factions
 * collecting parts from a shared factory using a thread pool.
 */
@Slf4j
public class Imitation {

    private final int days;

    /**
     *
     * @param days number of days to imitate
     */
    public Imitation(int days) {
        this.days = days;
    }

    /**
     * Runs the simulation for a fixed number of days:
     * each day the factory produces parts and both factions attempt to take them.
     * Finally prints the winner based on the number of buildable robots.
     * Blocks until imitation ends.
     */
    public void start() {
        try (var pool = Executors.newCachedThreadPool()) {
            var controller = new TimeController(days);
            var factory = new Factory(controller);
            var world = new Faction("world", factory, controller);
            var wednesday = new Faction("wednesday", factory, controller);
            pool.execute(factory);
            pool.execute(world);
            pool.execute(wednesday);
            controller.awaitEnd(3, TimeUnit.SECONDS);
            pool.shutdown();
            try {
                if (!pool.awaitTermination(3, TimeUnit.SECONDS)) {
                    pool.shutdownNow();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (world.getRobotsCount() == wednesday.getRobotsCount()) {
                log.info("It's a tie! Both factions have {} robots", world.getRobotsCount());
            } else {
                String winner = world.getRobotsCount() > wednesday.getRobotsCount() ?
                        world.getName() : wednesday.getName();
                log.info("The winner is {} ({} {}'s robots vs {} {}'s robots",
                        winner,
                        world.getRobotsCount(), world.getName(),
                        wednesday.getRobotsCount(), wednesday.getName());
            }
        }

    }


}
