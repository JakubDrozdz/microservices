package pl.jakubdrozdz.customer;

import org.springframework.stereotype.Service;
import pl.jakubdrozdz.clients.fraud.FraudCheckResponse;
import pl.jakubdrozdz.clients.fraud.FraudClient;

@Service
public record CustomerService(CustomerRepository customerRepository, FraudClient fraudClient) {
    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRegistrationRequest.firstName())
                .lastName(customerRegistrationRequest.lastName())
                .email(customerRegistrationRequest.email())
                .build();
        //todo: validate email
        //todo: check if email not used
        customerRepository.saveAndFlush(customer);
        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());
        if(fraudCheckResponse.isFraudster()){
            customerRepository.delete(customer);
            throw new IllegalStateException("fraudster");
        }
        //todo: send notification
    }
}
