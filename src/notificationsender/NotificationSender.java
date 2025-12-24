package notificationsender;

import notification.Notification;

public interface NotificationSender {
    void send(Notification notification);
}
