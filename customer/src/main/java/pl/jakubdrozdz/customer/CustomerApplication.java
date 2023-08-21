package pl.jakubdrozdz.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
        scanBasePackages = {
                "pl.jakubdrozdz.customer",
                "pl.jakubdrozdz.amqp"
        }
)
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "pl.jakubdrozdz.clients")
public class CustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }
}
