import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

public class Faction {

    private static final int TAKING_PART_COUNT_PER_DAY = 5;

    private final String name;
    private final Factory factory;
    private final Map<RobotPart, Integer> parts;
    private final Random random;

    public Faction(String name, Factory factory) {
        this.name = name;
        this.factory = factory;
        this.parts = new EnumMap<>(RobotPart.class);
        this.random = new Random();
    }

    public void startTakingParts() {
        for (int i = 0; i < TAKING_PART_COUNT_PER_DAY; i++) {
            factory.takePart().ifPresent(part ->
                    parts.put(part, parts.getOrDefault(part, 0) + 1));
            // To prevent predictable first-called Faction as a winner
            try {
                Thread.sleep(random.nextInt(0, 20));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int getRobotsCount() {
        return parts.values().stream()
                .mapToInt(Integer::intValue)
                .min()
                .orElse(0);
    }

    public String getName() {
        return name;
    }
}
