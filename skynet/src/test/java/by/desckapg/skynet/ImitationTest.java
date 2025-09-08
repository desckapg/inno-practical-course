package by.desckapg.skynet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.assertj.core.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ImitationTest {

    @Test
    void shouldRunImitation() {
        var imitation = new Imitation(100);
        assertThatNoException().isThrownBy(imitation::start);
    }

}
