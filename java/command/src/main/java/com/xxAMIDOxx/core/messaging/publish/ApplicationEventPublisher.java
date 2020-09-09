package com.xxAMIDOxx.core.messaging.publish;

import com.xxAMIDOxx.core.messaging.event.ApplicationEvent;

public interface ApplicationEventPublisher {

  void publish(ApplicationEvent applicationEvent);
}
