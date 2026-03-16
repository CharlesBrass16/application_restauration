/* (C)2024 */
package ca.ulaval.glo2003.domain.customer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo2003.domain.exceptions.InvalidParameterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerAttributesValidatorTest {
    public static final String VALID_NAME = "John Deer";
    public static final String VALID_EMAIL = "john.deer@gmail.com";
    public static final String VALID_PHONE_NUMBER = "1234567890";
    private CustomerAttributesValidator customerValidator;

    @BeforeEach
    void setupCustomerValidator() {
        customerValidator = new CustomerAttributesValidator();
    }

    @Test
    void givenValidCustomer_whenValidateCustomer_thenNoExceptionIsThrown() {
        assertDoesNotThrow(
                () -> customerValidator.validate(VALID_NAME, VALID_EMAIL, VALID_PHONE_NUMBER));
    }

    @Test
    void givenEmptyName_whenValidateCustomer_thenThrowsException() {
        String emptyName = "";

        assertThrows(
                InvalidParameterException.class,
                () -> customerValidator.validate(emptyName, VALID_EMAIL, VALID_PHONE_NUMBER));
    }

    @Test
    void givenNullName_whenValidateCustomer_thenThrowsException() {
        String nullName = null;

        assertThrows(
                InvalidParameterException.class,
                () -> customerValidator.validate(nullName, VALID_EMAIL, VALID_PHONE_NUMBER));
    }

    @Test
    void givenEmptyEmail_whenValidateCustomer_thenThrowsException() {
        String emptyEmail = "";

        assertThrows(
                InvalidParameterException.class,
                () -> customerValidator.validate(VALID_NAME, emptyEmail, VALID_PHONE_NUMBER));
    }

    @Test
    void givenNullEmail_whenValidateCustomer_thenThrowsException() {
        String nullEmail = null;

        assertThrows(
                InvalidParameterException.class,
                () -> customerValidator.validate(VALID_NAME, nullEmail, VALID_PHONE_NUMBER));
    }

    @Test
    void givenInvalidEmail_whenValidateCustomer_thenThrowsException() {
        String invalidEmail = "john.deer@gmail";

        assertThrows(
                InvalidParameterException.class,
                () -> customerValidator.validate(VALID_NAME, invalidEmail, VALID_PHONE_NUMBER));
    }

    @Test
    void givenEmptyPhoneNumber_whenValidateCustomer_thenThrowsException() {
        String emptyPhoneNumber = "";

        assertThrows(
                InvalidParameterException.class,
                () -> customerValidator.validate(VALID_NAME, VALID_EMAIL, emptyPhoneNumber));
    }

    @Test
    void givenNullPhoneNumber_whenValidateCustomer_thenThrowsException() {
        String nullPhoneNumber = null;

        assertThrows(
                InvalidParameterException.class,
                () -> customerValidator.validate(VALID_NAME, VALID_EMAIL, nullPhoneNumber));
    }

    @Test
    void givenInvalidPhoneNumber_whenValidateCustomer_thenThrowsException() {
        String invalidPhoneNumber = "123456789";

        assertThrows(
                InvalidParameterException.class,
                () -> customerValidator.validate(VALID_NAME, VALID_EMAIL, invalidPhoneNumber));
    }
}
