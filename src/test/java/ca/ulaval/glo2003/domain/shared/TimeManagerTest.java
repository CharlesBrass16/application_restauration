/* (C)2024 */
package ca.ulaval.glo2003.domain.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import org.junit.jupiter.api.Test;

public class TimeManagerTest {
    @Test
    void givenTimeNotMultipleOf15Minutes_whenGettingFirstFixedTime_thenReturnsAdjustedFixedTime() {
        assertEquals(LocalTime.of(0, 0, 0), TimeManager.getFirstFixedTime(LocalTime.of(23, 46, 0)));
    }

    @Test
    void givenTimeMultipleOf15Minutes_whenGettingFirstFixedTime_thenReturnsSameTime() {
        assertEquals(
                LocalTime.of(23, 45, 0), TimeManager.getFirstFixedTime(LocalTime.of(23, 45, 0)));
    }

    @Test
    void givenTimeNotMultipleOf15Minutes_whenGettingLastFixedTime_thenReturnsAdjustedFixedTime() {
        assertEquals(LocalTime.of(0, 0, 0), TimeManager.getLastFixedTime(LocalTime.of(0, 14, 0)));
    }

    @Test
    void givenTimeMultipleOf15Minutes_whenGettingLastFixedTime_thenReturnsSameTime() {
        assertEquals(LocalTime.of(0, 15, 0), TimeManager.getLastFixedTime(LocalTime.of(0, 15, 0)));
    }

    @Test
    void givenTimeNotMultipleOf15Minutes_whenGettingNextFixedTime_thenReturnsAdjustedFixedTime() {
        assertEquals(LocalTime.of(0, 0, 0), TimeManager.getNextFixedTime(LocalTime.of(23, 46, 0)));
    }

    @Test
    void givenTimeMultipleOf15Minutes_whenGettingNextFixedTime_thenReturnsAdjustedFixedTime() {
        assertEquals(LocalTime.of(0, 0, 0), TimeManager.getNextFixedTime(LocalTime.of(23, 45, 0)));
    }
}
