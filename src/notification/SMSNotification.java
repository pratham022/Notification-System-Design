package notification;

import enums.Channel;

public class SMSNotification implements Notification {
    private final Channel channel;
    private final String content;
    private final String recipient;

    public SMSNotification(Channel channel, String content, String recipient) {
        this.channel = channel;
        this.content = content;
        this.recipient = recipient;
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String getRecipient() {
        return recipient;
    }
}
