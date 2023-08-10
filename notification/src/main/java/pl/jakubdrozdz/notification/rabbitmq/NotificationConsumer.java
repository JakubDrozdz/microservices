package pl.jakubdrozdz.notification.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pl.jakubdrozdz.clients.notification.NotificationRequest;
import pl.jakubdrozdz.notification.NotificationService;

@Component
@Slf4j
public record NotificationConsumer(NotificationService notificationService) {
    @RabbitListener(queues = "${rabbitmq.queues.notification}")
    public void consumer(NotificationRequest notificationRequest) {
        log.info("Consumed {} from queue", notificationRequest);
        notificationService.send(notificationRequest);
    }
}
