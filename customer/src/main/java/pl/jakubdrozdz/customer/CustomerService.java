package pl.jakubdrozdz.customer;

import org.springframework.stereotype.Service;
import pl.jakubdrozdz.clients.fraud.FraudCheckResponse;
import pl.jakubdrozdz.clients.fraud.FraudClient;

import java.util.regex.Pattern;

@Service
public record CustomerService(CustomerRepository customerRepository, FraudClient fraudClient) {
    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRegistrationRequest.firstName())
                .lastName(customerRegistrationRequest.lastName())
                .email(customerRegistrationRequest.email())
                .build();
        boolean isEmailValid = validateEmail(customer.getEmail());
        if (isEmailValid) {
            //todo: check if email not used
            customerRepository.saveAndFlush(customer);
            FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());
            if (fraudCheckResponse.isFraudster()) {
                customerRepository.delete(customer);
                throw new IllegalStateException("fraudster");
            }

        } else {
            throw new IllegalArgumentException("invalid email");
        }
        //todo: send notification
    }

    private boolean validateEmail(String email) {
        return Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$").matcher(email).find();
    }
}
