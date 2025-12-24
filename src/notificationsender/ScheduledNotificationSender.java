package notificationsender;

import notification.Notification;

public interface ScheduledNotificationSender extends NotificationSender {
    void schedule(Notification notification, long delaySeconds);
}
