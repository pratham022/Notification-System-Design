package factory;

import enums.Channel;
import notificationsender.ScheduledNotificationSender;

import java.util.Optional;

public interface NotificationSenderFactory {
    Optional<ScheduledNotificationSender> getNotificationSender(Channel channel);
}
