/* (C)2024 */
package ca.ulaval.glo2003.domain.customer;

import jakarta.inject.Inject;

public class CustomerFactory {

    private final CustomerAttributesValidator customerAttributesValidator;

    @Inject
    public CustomerFactory(CustomerAttributesValidator customerAttributesValidator) {
        this.customerAttributesValidator = customerAttributesValidator;
    }

    public Customer create(String name, String email, String phoneNumber) {
        customerAttributesValidator.validate(name, email, phoneNumber);

        return new Customer(name, email, phoneNumber);
    }
}
