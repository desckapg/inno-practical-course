import java.util.Optional;
import java.util.Random;
import java.util.Stack;

public class Factory {

    private static final int MAX_PRODUCED_PART_PER_DAY = 10;
    private final Random random;
    private final Stack<RobotPart> parts;

    public Factory() {
        this.random = new Random();
        this.parts = new Stack<>();
    }

    public void produceParts() {
        int partsCount = random.nextInt(MAX_PRODUCED_PART_PER_DAY + 1);
        for (; partsCount > 0; partsCount--) {
            int partOrdinal = random.nextInt(RobotPart.values().length);
            parts.push(RobotPart.values()[partOrdinal]);
        }
    }

    public synchronized Optional<RobotPart> takePart() {
        if (parts.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(parts.pop());
    }


}
