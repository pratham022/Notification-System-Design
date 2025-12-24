import enums.Channel;
import factory.DefaultNotificationSenderFactory;
import factory.NotificationSenderFactory;
import notification.EmailNotification;
import notification.Notification;
import notification.SMSNotification;

public class Main {
    public static void main(String[] args) {
        NotificationSenderFactory notificationSenderFactory = new DefaultNotificationSenderFactory();
        NotificationDispatcher notificationDispatcher = new NotificationDispatcher(notificationSenderFactory);

        Notification emailNotification = new EmailNotification(Channel.EMAIL, "Email notification content", "john@gmail.com");
        Notification smsNotification = new SMSNotification(Channel.SMS, "SMS notification content", "+91-12345");

        notificationDispatcher.dispatch(emailNotification);
        notificationDispatcher.dispatch(smsNotification);

        notificationDispatcher.schedule(emailNotification, 10);
        notificationDispatcher.schedule(smsNotification, 12);
    }
}