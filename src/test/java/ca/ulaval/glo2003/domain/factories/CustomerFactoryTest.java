/* (C)2024 */
package ca.ulaval.glo2003.domain.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo2003.domain.customer.Customer;
import ca.ulaval.glo2003.domain.customer.CustomerAttributesValidator;
import ca.ulaval.glo2003.domain.customer.CustomerFactory;
import org.junit.jupiter.api.Test;

class CustomerFactoryTest {

    public static final CustomerAttributesValidator CUSTOMER_ATTRIBUTES_VALIDATOR =
            new CustomerAttributesValidator();
    public static final CustomerFactory CUSTOMER_FACTORY =
            new CustomerFactory(CUSTOMER_ATTRIBUTES_VALIDATOR);
    private static final String NAME = "John Deer";
    private static final String EMAIL = "john.deer@gmail.com";
    private static final String PHONE_NUMBER = "1234567890";

    @Test
    void givenValidParameters_whenCreate_thenCustomerIsCreated() {
        var expected = new Customer(NAME, EMAIL, PHONE_NUMBER);

        var actual = CUSTOMER_FACTORY.create(NAME, EMAIL, PHONE_NUMBER);

        assertEquals(expected, actual);
    }
}
