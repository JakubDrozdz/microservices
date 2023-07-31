package pl.jakubdrozdz.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.jakubdrozdz.clients.fraud.FraudCheckResponse;
import pl.jakubdrozdz.clients.fraud.FraudClient;
import pl.jakubdrozdz.customer.exceptions.EmailNotUniqueException;
import pl.jakubdrozdz.customer.exceptions.UserNotValidatedException;

import java.util.regex.Pattern;

@Service
@Slf4j
public record CustomerService(CustomerRepository customerRepository, FraudClient fraudClient) {
    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRegistrationRequest.firstName())
                .lastName(customerRegistrationRequest.lastName())
                .email(customerRegistrationRequest.email())
                .build();
        try {
            validateEmail(customer.getEmail());
            checkUniqueEmail(customer.getEmail());
            customerRepository.saveAndFlush(customer);
            FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());
            if (fraudCheckResponse.isFraudster()) {
                customerRepository.delete(customer);
                throw new IllegalStateException("fraudster");
            }

        } catch (IllegalArgumentException | EmailNotUniqueException ex) {
            //log.error(ex.getLocalizedMessage(), ex);
            log.error(ex.getLocalizedMessage());
            throw new UserNotValidatedException("API ERROR");
        }
        //todo: send notification
    }

    private boolean validateEmail(String email) throws IllegalArgumentException {
        if (!Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$").matcher(email).find()) {
            throw new IllegalArgumentException("Invalid email address " + email);
        }
        return true;
    }

    private boolean checkUniqueEmail(String email) throws EmailNotUniqueException {
        if (!customerRepository.findCustomersByEmail(email).isEmpty()) {
            throw new EmailNotUniqueException("Email " + email + " already present in database");
        }
        return true;
    }
}
