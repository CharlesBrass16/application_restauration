/* (C)2024 */
package ca.ulaval.glo2003.domain.customer;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerAttributesValidator {
    public void validate(String name, String email, String phoneNumber) {
        validateName(name);
        validateEmail(email);
        validatePhoneNumber(phoneNumber);
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidParameterException("Le nom du client ne peut pas être vide");
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new InvalidParameterException(
                    "L'adresse courriel du client ne peut pas être vide");
        }
        String emailValidationRegex =
                "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!isMatch(emailValidationRegex, email)) {
            throw new InvalidParameterException("L'adresse courriel du client n'est pas valide");
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new InvalidParameterException(
                    "Le numéro de téléphone du client ne peut pas être vide");
        }
        String phoneNumberValidationRegex = "^\\d{10}$";
        if (!isMatch(phoneNumberValidationRegex, phoneNumber)) {
            throw new InvalidParameterException(
                    "Le numéro de téléphone du client n'est pas valide");
        }
    }

    private boolean isMatch(String regex, String stringToMatch) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(stringToMatch);
        return matcher.find();
    }
}
