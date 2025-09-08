package by.desckapg.skynet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FactionTest {

    @Test
    void constructor_constructorCalled_registersInControllerAndSetsName() {
        Factory factory = mock(Factory.class);
        TimeController controller = mock(TimeController.class);

        Faction faction = new Faction("world", factory, controller);

        verify(controller, times(1)).register();
        assertThat(faction.getName()).isEqualTo("world");
    }

    @Test
    void run_takesExactlyFiveParts_shouldReturnZeroRobotsCount() {
        Factory factory = mock(Factory.class);
        TimeController controller = mock(TimeController.class);

        when(controller.isRunning()).thenReturn(true, false);

        RobotPart p0 = RobotPart.HAND;
        when(factory.takePart()).thenReturn(
                Optional.of(p0),
                Optional.of(p0),
                Optional.of(p0),
                Optional.of(p0),
                Optional.of(p0)
        );

        Faction faction = new Faction("world", factory, controller);
        faction.run();

        verify(controller, times(1)).waitNight();
        verify(factory, times(5)).takePart();
        verify(controller, times(1)).markNightEnd();

        assertThat(faction.getRobotsCount()).isEqualTo(0);
    }

    @Test
    void run_takesExactlyTenParts_shouldReturnOneRobotsCount() {
        Factory factory = mock(Factory.class);
        TimeController controller = mock(TimeController.class);

        when(controller.isRunning()).thenReturn(true, true, false);

        RobotPart p0 = RobotPart.values()[0];
        RobotPart p1 = RobotPart.values()[1];
        RobotPart p2 = RobotPart.values()[2];
        RobotPart p3 = RobotPart.values()[3];

        when(factory.takePart()).thenReturn(
                Optional.of(p0), Optional.of(p1), Optional.of(p2),
                Optional.of(p3), Optional.of(p1), Optional.of(p0),
                Optional.of(p0), Optional.of(p0), Optional.of(p0),
                Optional.of(p3)
        );

        Faction faction = new Faction("world", factory, controller);
        faction.run();

        verify(factory, times(10)).takePart();
        verify(controller, times(2)).waitNight();
        verify(controller, times(2)).markNightEnd();

        assertThat(faction.getRobotsCount()).isEqualTo(1);
    }

    @Test
    void run_takesTwoParts_shouldReturnZeroRobotsCount() {
        Factory factory = mock(Factory.class);
        TimeController controller = mock(TimeController.class);

        when(controller.isRunning()).thenReturn(true, false);

        RobotPart p0 = RobotPart.values()[0];
        RobotPart p1 = RobotPart.values()[1];

        when(factory.takePart()).thenReturn(
                Optional.of(p0),
                Optional.empty(),
                Optional.of(p1),
                Optional.empty(),
                Optional.empty()
        );

        Faction faction = new Faction("world", factory, controller);
        faction.run();

        verify(factory, times(5)).takePart();
        assertThat(faction.getRobotsCount()).isEqualTo(0);
    }

    @Test
    void run_oneNight_allEmpty_returnsZeroRobots() {
        Factory factory = mock(Factory.class);
        TimeController controller = mock(TimeController.class);

        when(controller.isRunning()).thenReturn(true, false);
        when(factory.takePart()).thenReturn(
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );

        Faction faction = new Faction("world", factory, controller);
        faction.run();

        verify(factory, times(5)).takePart();
        assertThat(faction.getRobotsCount()).isEqualTo(0);
    }
}
