/* (C)2024 */
package ca.ulaval.glo2003.domain.shared;

import java.time.LocalTime;

public record TimeManager() {
    private static final int FIXED_TIME_STEP = 15;

    public static LocalTime getFirstFixedTime(LocalTime time) {
        int offset = (FIXED_TIME_STEP - (time.getMinute() % FIXED_TIME_STEP)) % FIXED_TIME_STEP;
        return time.plusMinutes(offset);
    }

    public static LocalTime getLastFixedTime(LocalTime time) {
        int offset = time.getMinute() % FIXED_TIME_STEP;
        return time.minusMinutes(offset);
    }

    public static LocalTime getNextFixedTime(LocalTime time) {
        int offset = FIXED_TIME_STEP - (time.getMinute() % FIXED_TIME_STEP);
        return time.plusMinutes(offset);
    }
}
