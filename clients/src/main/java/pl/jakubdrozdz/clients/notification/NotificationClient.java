package pl.jakubdrozdz.clients.notification;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.jakubdrozdz.clients.fraud.FraudCheckResponse;

@FeignClient("notification")
public interface NotificationClient {
    @PostMapping(path = "api/v1/notification")
    void sendNotification(NotificationRequest notificationRequest);
}
