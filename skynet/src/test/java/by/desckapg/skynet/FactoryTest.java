package by.desckapg.skynet;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public class FactoryTest {

    @Test
    void constructor_constructorCalled_registersInController() {
        var timeController = mock(TimeController.class);
        new Factory(timeController);
        verify(timeController, times(1)).register();
    }

    @Test
    void run_produceSomeParts_shouldCallDayPhaseMethods() {
        var timeController = mock(TimeController.class);
        when(timeController.isRunning()).thenReturn(true, false);

        Factory factory = new Factory(timeController);

        factory.run();

        verify(timeController, times(1)).markDayEnd();
        verify(timeController, times(1)).waitDay();

    }
}
