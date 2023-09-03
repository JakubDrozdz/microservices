package pl.jakubdrozdz.customer;

import org.springframework.stereotype.Component;
import pl.jakubdrozdz.customer.exceptions.EmailNotUniqueException;

import java.util.regex.Pattern;

@Component
public class CustomerUtils {
    CustomerRepository customerRepository;
    public CustomerUtils(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
    protected boolean validateEmail(String email) throws IllegalArgumentException {
        if (!Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$").matcher(email).find()) {
            throw new IllegalArgumentException("Invalid email address " + email);
        }
        return true;
    }

    protected boolean checkUniqueEmail(String email) throws EmailNotUniqueException {
        if (!customerRepository.findCustomersByEmail(email).isEmpty()) {
            throw new EmailNotUniqueException("Email " + email + " already present in database");
        }
        return true;
    }
}
