import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Horse.class)
class HorseTest {
    Horse testHorse = new Horse("nameForTest", 2.0);
    Horse testHorseWithAllParameter = new Horse("nameForTest", 2.0, 5.0);

    @Test
    public void constructorWithNameEqualsNull() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, 0.0);
        });
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\n"})
    public void constructorWithNameIsEmpty(String invalidName) {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(invalidName, 0.0);
        });
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    public void constructorWithNegativeSpeed() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("nameForTest", -1.0);
        });
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    public void constructorWithNegativeDistance() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("nameForTest", 0.0, -1.0);
        });
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    public void getName() {
        assertEquals("nameForTest", testHorse.getName());
    }

    @Test
    public void getSpeed() {
        assertEquals(2.0, testHorse.getSpeed());
    }

    @Test
    public void getDistance() {
        assertEquals(5.0, testHorseWithAllParameter.getDistance());
        assertEquals(0.0, testHorse.getDistance());
    }

    @Test
    public void moveCallGetRandomDouble() {
        try (MockedStatic<Horse> mockObj = mockStatic(Horse.class)) {
            testHorse.move();
            mockObj.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.3, 0.5, 0.7, 0.9})
    public void moveAssignValueToDistance(Double possibleArgument) {
        try (MockedStatic<Horse> mockObj = mockStatic(Horse.class)) {
            mockObj.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(possibleArgument);
            Double probMove = testHorseWithAllParameter.getDistance()
                    + testHorseWithAllParameter.getSpeed()
                    * Horse.getRandomDouble(0.2, 0.9);
            testHorseWithAllParameter.move();
            assertEquals(probMove, testHorseWithAllParameter.getDistance());
        }
    }
}
