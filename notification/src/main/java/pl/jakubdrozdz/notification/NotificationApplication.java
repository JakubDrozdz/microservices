package pl.jakubdrozdz.notification;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(
        scanBasePackages = {
                "pl.jakubdrozdz.notification",
                "pl.jakubdrozdz.amqp"
        }
)
@EnableDiscoveryClient
@OpenAPIDefinition(info =
@Info(title = "Notification API", version = "1.0", description = "Notification API v1.0 documentation")
)
public class NotificationApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }
}
