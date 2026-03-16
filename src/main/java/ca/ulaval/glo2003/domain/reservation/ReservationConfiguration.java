/* (C)2024 */
package ca.ulaval.glo2003.domain.reservation;

public class ReservationConfiguration {
    private final int durationInMinutes;

    public ReservationConfiguration(int duration) {
        this.durationInMinutes = duration;
    }

    public static ReservationConfiguration Default() {
        return new ReservationConfiguration(60);
    }

    public int getDuration() {
        return durationInMinutes;
    }
}
