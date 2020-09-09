package com.xxAMIDOxx.core.servicebus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.microsoft.azure.servicebus.Message;
import com.microsoft.azure.servicebus.TopicClient;
import com.xxAMIDOxx.core.messaging.event.ApplicationEvent;
import com.xxAMIDOxx.core.messaging.publish.ApplicationEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.Duration;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
@ConditionalOnProperty(value = "azure.servicebus.enabled", havingValue = "true")
public class UpdateEventServiceBusDispatcher implements ApplicationEventPublisher {

  Logger logger = LoggerFactory.getLogger(UpdateEventServiceBusDispatcher.class);

  JsonMapper jsonMapper;
  TopicClient topicClient;

  public UpdateEventServiceBusDispatcher(TopicClient topicClient, JsonMapper jsonMapper) {
    this.topicClient = topicClient;
    this.jsonMapper = jsonMapper;
  }

  @Override
  public void publish(ApplicationEvent applicationEvent) {

    try {

      Message message = createMessageFromEvent(applicationEvent);

      logger.debug("Message sending: Id = {}", message.getMessageId());

      topicClient
          .sendAsync(message)
          .thenRunAsync(
              () -> logger.debug("Message acknowledged: Id = {}", message.getMessageId()));

    } catch (JsonProcessingException e) {
      logger.error("Unable to process ApplicationEvent", e);
    }
  }

  public Message createMessageFromEvent(ApplicationEvent applicationEvent)
      throws JsonProcessingException {
    String content = jsonMapper.writeValueAsString(applicationEvent);
    Message message = new Message(content.getBytes(UTF_8));
    message.setContentType("application/json");
    message.setLabel(applicationEvent.getClass().getSimpleName());
    message.setMessageId(applicationEvent.getId().toString());
    message.setTimeToLive(Duration.ofMinutes(2));
    return message;
  }
}
