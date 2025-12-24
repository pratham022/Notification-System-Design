package notificationsender;

import notification.Notification;

public class SMSNotificationSender implements ScheduledNotificationSender {
    @Override
    public void schedule(Notification notification, long delaySeconds) {
        long scheduledAt = System.currentTimeMillis() + delaySeconds * 1000;

        System.out.println(
                "[SMS] -> To: " + notification.getRecipient()
                        + " | Content: " + notification.getContent()
                        + " | ScheduledAt(ms): " + scheduledAt
        );
    }

    @Override
    public void send(Notification notification) {
        System.out.println(
                "[SMS] -> To: " + notification.getRecipient()
                        + " | Content: " + notification.getContent()
                        + " | SentAt(ms): " + System.currentTimeMillis()
        );
    }
}
