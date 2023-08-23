package pl.jakubdrozdz.customer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
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
@OpenAPIDefinition(info =
@Info(title = "Customer API", version = "1.0", description = "Customer API v1.0 documentation")
)
public class CustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }
}
