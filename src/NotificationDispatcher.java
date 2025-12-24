import factory.NotificationSenderFactory;
import notification.Notification;

public class NotificationDispatcher {
    private final NotificationSenderFactory notificationSenderFactory;

    public NotificationDispatcher(NotificationSenderFactory notificationSenderFactory) {
        this.notificationSenderFactory = notificationSenderFactory;
    }

    public void dispatch(Notification notification) {
        notificationSenderFactory.getNotificationSender(notification.getChannel())
                .ifPresentOrElse(sender -> sender.send(notification),
                        () -> System.out.println("Unsupported notification channel"));
    }

    public void schedule(Notification notification, long delaySeconds) {
        notificationSenderFactory.getNotificationSender(notification.getChannel())
                .ifPresentOrElse(sender -> sender.schedule(notification, delaySeconds),
                        () -> System.out.println("Unsupported notification channel"));
    }
}
