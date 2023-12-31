package pl.jakubdrozdz.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.jakubdrozdz.clients.notification.NotificationRequest;

@RestController
@RequestMapping("api/v1/notification")
@Slf4j
@CrossOrigin(origins = "http://localhost")
public record NotificationController(NotificationService notificationService) {
    @PostMapping
    public void sendNotification(@RequestBody NotificationRequest notificationRequest) {
        log.info("New notification: {}", notificationRequest);
        notificationService.send(notificationRequest);
    }
}
