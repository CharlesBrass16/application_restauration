/* (C)2024 */
package ca.ulaval.glo2003.domain.reservation;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import ca.ulaval.glo2003.domain.restaurant.Restaurant;
import java.time.LocalTime;

public class ReservationValidator {
    public void validate(
            LocalTime startTime, LocalTime endTime, int groupSize, Restaurant restaurant) {
        validateStartTime(startTime, restaurant);
        validateEndTime(endTime, restaurant);
        validateGroupSize(groupSize);
    }

    private void validateStartTime(LocalTime startTime, Restaurant restaurant) {
        if (startTime.isBefore(restaurant.getOpeningTime())) {
            throw new InvalidParameterException(
                    "L'heure de début de la réservation ne doit pas être avant l'heure d'ouverture"
                            + " du restaurant");
        }
    }

    private void validateEndTime(LocalTime endTime, Restaurant restaurant) {
        if (endTime.isAfter(restaurant.getClosingTime())) {
            throw new InvalidParameterException(
                    "L'heure de fin de la réservation ne doit pas dépasser l'heure de fermeture du"
                            + " restaurant");
        }
    }

    private void validateGroupSize(int groupSize) {
        if (groupSize < 1) {
            throw new InvalidParameterException("La taille du groupe doit être d'au moins 1");
        }
    }
}
