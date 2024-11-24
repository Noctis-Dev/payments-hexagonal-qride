package com.qride.payments.domain.broker;

import com.qride.payments.domain.events.NotificationEvent;

public interface IMessageProducer {

    void sendNotification(NotificationEvent event);

}
