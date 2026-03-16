/* (C)2024 */
package ca.ulaval.glo2003.domain.restaurant;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import java.time.LocalTime;

public class RestaurantValidator {

    public void validate(String name, int capacity, LocalTime openingTime, LocalTime closingTime) {
        validateName(name);
        validateCapacity(capacity);
        validateHours(openingTime, closingTime);
    }

    private void validateHours(LocalTime openingTime, LocalTime closingTime) {
        if (openingTime.plusHours(1).toSecondOfDay() > closingTime.toSecondOfDay()) {
            throw new InvalidParameterException(
                    "Le restaurant doit être ouvert durant au moins une heure");
        }
    }

    private void validateCapacity(int capacity) {
        if (capacity <= 0) {
            throw new InvalidParameterException(
                    "La capacité ne peut pas être inférieur à 1 ou être vide");
        }
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidParameterException("Le nom du restaurant ne peut pas être vide");
        }
    }
}
