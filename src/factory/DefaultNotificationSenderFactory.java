package factory;

import enums.Channel;
import notificationsender.EmailNotificationSender;
import notificationsender.SMSNotificationSender;
import notificationsender.ScheduledNotificationSender;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DefaultNotificationSenderFactory implements NotificationSenderFactory {

    private final Map<Channel, ScheduledNotificationSender> notificationSenderMap;

    public DefaultNotificationSenderFactory() {
        notificationSenderMap = new HashMap<>();
        notificationSenderMap.put(Channel.EMAIL, new EmailNotificationSender());
        notificationSenderMap.put(Channel.SMS, new SMSNotificationSender());
    }

    @Override
    public Optional<ScheduledNotificationSender> getNotificationSender(Channel channel) {
        return Optional.ofNullable(notificationSenderMap.get(channel));
    }
}
