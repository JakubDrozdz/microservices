package pl.jakubdrozdz.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.jakubdrozdz.amqp.RabbitMQMessageProducer;
import pl.jakubdrozdz.clients.fraud.FraudCheckResponse;
import pl.jakubdrozdz.clients.fraud.FraudClient;
import pl.jakubdrozdz.clients.notification.NotificationClient;
import pl.jakubdrozdz.clients.notification.NotificationRequest;
import pl.jakubdrozdz.customer.exceptions.EmailNotUniqueException;
import pl.jakubdrozdz.customer.exceptions.UserNotValidatedException;


@Service
@Slf4j
public record CustomerService(CustomerRepository customerRepository, FraudClient fraudClient,
                              NotificationClient notificationClient, RabbitMQMessageProducer rabbitMQMessageProducer, CustomerUtils customerUtils) {
    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRegistrationRequest.firstName())
                .lastName(customerRegistrationRequest.lastName())
                .email(customerRegistrationRequest.email())
                .build();
        try {
            customerUtils.validateEmail(customer.getEmail());
            customerUtils.checkUniqueEmail(customer.getEmail());
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

        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Customer %s %s has been registered", customer.getFirstName(), customer.getLastName())
        );

        rabbitMQMessageProducer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key"
        );
    }
}
