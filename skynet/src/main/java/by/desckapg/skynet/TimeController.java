package by.desckapg.skynet;

import lombok.Getter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

@Getter
public class TimeController {

    private final int maxDays;
    private final CountDownLatch latch = new CountDownLatch(1);
    private final Phaser phaser = new Phaser();

    /**
     * Creates a time controller with maxDays constraint.
     *
     * @param maxDays day count
     */
    public TimeController(int maxDays) {
        this.maxDays = maxDays;
    }

    /**
     * Forces called thread to wait reaching max day.
     */
    public void awaitEnd(long timeout, TimeUnit unit) {
        try {
            latch.await(timeout, unit);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Registers thread.
     */
    public void register() {
        phaser.register();
    }

    /**
     * Forces called thread to wait next day phase.
     */
    public void waitDay() {
        phaser.arriveAndAwaitAdvance();
    }

    /**
     * Forces called thread to wait next night phase.
     */
    public void waitNight() {
        phaser.arriveAndAwaitAdvance();
    }

    /**
     * Marks night end for called thread.
     */
    public void markNightEnd() {
        phaser.arriveAndAwaitAdvance();
    }

    /**
     * Marks day end for called thread.
     */
    public void markDayEnd() {
        phaser.arriveAndAwaitAdvance();
        if (!isRunning()) {
            latch.countDown();
        }
    }
    /**
     * Creates a new factory with external time controller.
     *
     * @return true if the simulation is still running; false if max days reached
     */
    public boolean isRunning() {
        return phaser.getPhase() / 2 < maxDays;
    }

}
