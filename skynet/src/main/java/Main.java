import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class Main {

    private static final int DAYS_COUNT = 100;

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
