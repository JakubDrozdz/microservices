package pl.jakubdrozdz.customer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import pl.jakubdrozdz.customer.exceptions.EmailNotUniqueException;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;

class CustomerUtilsTest {
    @InjectMocks
    private CustomerUtils customerUtils;
    @Mock
    private CustomerRepository customerRepository;

    private Customer customer;

    private String email;

    @BeforeEach
    void setUp(){
        customerRepository = Mockito.mock(CustomerRepository.class);
        customerUtils = new CustomerUtils(customerRepository);
        customer = Customer.builder().id(1).firstName("John").lastName("Doe").email(email).build();
        email = "test@test.com";
    }

    @Test
    void validateEmailSuccessTest(){
        Assertions.assertTrue(customerUtils.validateEmail(email));
    }
    @Test
    void validateEmailFailTest(){
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> customerUtils.validateEmail("testtest.com"));
    }
    @Test
    void validateEmailFail2Test(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> customerUtils.validateEmail("test@testcom"));
    }
    @Test
    void validateEmailFail3Test(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> customerUtils.validateEmail("test@test@test2.com"));
    }
    @Test
    void validateEmailEmptyEmailTest(){
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> customerUtils.validateEmail(""));
    }
    @Test
    void checkUniqueEmailSuccessTest(){
        given(customerRepository.findCustomersByEmail(email)).willReturn(Collections.emptyList());
        Assertions.assertTrue(customerUtils.checkUniqueEmail(email));
    }
    @Test
    void checkUniqueEmailFailTest(){
        given(customerRepository.findCustomersByEmail(email)).willReturn(List.of(customer));
        Assertions.assertThrows(EmailNotUniqueException.class, () -> customerUtils.checkUniqueEmail(email));
    }
}
