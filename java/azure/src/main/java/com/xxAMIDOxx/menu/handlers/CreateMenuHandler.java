package com.xxAMIDOxx.menu.handlers;

import com.xxAMIDOxx.menu.repository.AzureMenuRepository;
import com.xxAMIDOxx.core.messaging.publish.ApplicationEventPublisher;
import com.xxAMIDOxx.menu.commands.CreateMenuCommand;
import com.xxAMIDOxx.menu.domain.Menu;
import com.xxAMIDOxx.menu.events.MenuCreatedEvent;
import com.xxAMIDOxx.menu.exception.MenuAlreadyExistsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import com.xxAMIDOxx.core.cqrs.handler.CommandHandler;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Component
public class CreateMenuHandler implements CommandHandler<CreateMenuCommand> {

  protected AzureMenuRepository menuRepository;

  private ApplicationEventPublisher applicationEventPublisher;

  public CreateMenuHandler(
      AzureMenuRepository menuRepository, ApplicationEventPublisher applicationEventPublisher) {
    this.menuRepository = menuRepository;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @Override
  public Optional<UUID> handle(CreateMenuCommand command) {

    verifyMenuNotAlreadyExisting(command);

    final UUID id = UUID.randomUUID();
    final Menu menu =
        new Menu(
            id.toString(),
            command.getRestaurantId().toString(),
            command.getName(),
            command.getDescription(),
            new ArrayList<>(),
            command.getEnabled());

    menuRepository.save(menu);

    applicationEventPublisher.publish(new MenuCreatedEvent(command, id));

    return Optional.of(id);
  }

  protected void verifyMenuNotAlreadyExisting(CreateMenuCommand command) {
    Page<Menu> existing =
        menuRepository.findAllByRestaurantIdAndName(
            command.getRestaurantId().toString(), command.getName(), PageRequest.of(0, 1));
    if (!existing.getContent().isEmpty()
        && existing.get().anyMatch(m -> m.getName().equals(command.getName()))) {
      throw new MenuAlreadyExistsException(command, command.getRestaurantId(), command.getName());
    }
  }
}
