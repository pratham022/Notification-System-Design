package notification;

import enums.Channel;

public interface Notification {
    Channel getChannel();
    String getContent();
    String getRecipient();
}
