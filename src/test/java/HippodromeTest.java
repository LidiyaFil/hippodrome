import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HippodromeTest {

    @Test
    public void constructorWithNull() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(null);
        });
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    public void constructorWithEmptyList() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            List<Horse> emptylist = new ArrayList<>();
            new Hippodrome(emptylist);
        });
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    public void getHorses() {
        List<Horse> initialHorses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Horse horse = mock(Horse.class);
            initialHorses.add(horse);
        }
        Hippodrome hippodrome = new Hippodrome(initialHorses);
        assertIterableEquals(initialHorses, hippodrome.getHorses());
    }

    @Test
    public void move() {
        List<Horse> initialHorses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Horse horse = mock(Horse.class);
            initialHorses.add(horse);
        }
        Hippodrome hippodrome = new Hippodrome(initialHorses);
        hippodrome.move();

        for (Horse mockHorse : initialHorses) {
            verify(mockHorse, times(1)).move();
        }
    }

    @ParameterizedTest
    @CsvSource({
            "10.0, 20.0, 15.0, 20.0",
            "25.0, 18.0, 30.0, 30.0",
            "5.0, 12.0, 8.0, 12.0"})
    public void getWinner(double distance1, double distance2, double distance3, double expectedResult) {
        Horse horse1 = mock(Horse.class);
        when(horse1.getDistance()).thenReturn(distance1);

        Horse horse2 = mock(Horse.class);
        when(horse2.getDistance()).thenReturn(distance2);

        Horse horse3 = mock(Horse.class);
        when(horse3.getDistance()).thenReturn(distance3);

        List<Horse> initialHorses = new ArrayList<>();
        initialHorses.add(horse1);
        initialHorses.add(horse2);
        initialHorses.add(horse3);

        Hippodrome hippodrome = new Hippodrome(initialHorses);
        Horse winner = hippodrome.getWinner();

        assertEquals(expectedResult, winner.getDistance());
    }
}
