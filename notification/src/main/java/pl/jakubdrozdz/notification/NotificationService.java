package pl.jakubdrozdz.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.jakubdrozdz.clients.notification.NotificationRequest;

import java.time.LocalDateTime;

@Service
public record NotificationService(NotificationRepository notificationRepository) {
    public void send(NotificationRequest notificationRequest) {
        notificationRepository.save(
                Notification.builder()
                        .toCustomerId(notificationRequest.toCustomerId())
                        .toCustomerEmail(notificationRequest.toCustomerEmail())
                        .sender("test@test.test")
                        .message(notificationRequest.message())
                        .sentAt(LocalDateTime.now())
                        .build()
        );
    }
}
