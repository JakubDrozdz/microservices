package pl.jakubdrozdz.notification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.jakubdrozdz.clients.notification.NotificationRequest;

import java.time.LocalDateTime;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    @Value("${sender.mail}")
    private String senderMail;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void send(NotificationRequest notificationRequest) {
        notificationRepository.save(
                Notification.builder()
                        .toCustomerId(notificationRequest.toCustomerId())
                        .toCustomerEmail(notificationRequest.toCustomerEmail())
                        .sender(senderMail)
                        .message(notificationRequest.message())
                        .sentAt(LocalDateTime.now())
                        .build()
        );
    }
}
