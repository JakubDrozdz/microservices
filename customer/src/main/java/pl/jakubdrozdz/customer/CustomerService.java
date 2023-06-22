package pl.jakubdrozdz.customer;

import org.springframework.stereotype.Service;

@Service
public record CustomerService(CustomerRepository customerRepository, CustomerConfig customerConfig) {
    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRegistrationRequest.firstName())
                .lastName(customerRegistrationRequest.lastName())
                .email(customerRegistrationRequest.email())
                .build();
        //todo: validate email
        //todo: check if email not used
        customerRepository.saveAndFlush(customer);
        FraudCheckResponse fraudCheckResponse = customerConfig.restTemplate().getForObject(
                "http://FRAUD/api/v1/fraud-check/{customerId}",
                FraudCheckResponse.class,
                customer.getId()
        );
        if(fraudCheckResponse.isFraudster()){
            customerRepository.delete(customer);
            throw new IllegalStateException("fraudster");
        }
        //todo: send notification
    }
}
