package com.xxAMIDOxx.menu.handlers;

import com.xxAMIDOxx.core.cqrs.handler.CommandHandler;
import com.xxAMIDOxx.core.messaging.publish.ApplicationEventPublisher;
import com.xxAMIDOxx.menu.commands.CreateMenuCommand;
import com.xxAMIDOxx.menu.domain.Menu;
import com.xxAMIDOxx.menu.events.MenuCreatedEvent;
import com.xxAMIDOxx.menu.exception.MenuAlreadyExistsException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import com.xxAMIDOxx.menu.repository.MenuFacade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class CreateMenuHandler implements CommandHandler<CreateMenuCommand> {

  protected MenuFacade menuFacade;
  private ApplicationEventPublisher applicationEventPublisher;

  public CreateMenuHandler(
    MenuFacade menuFacade, ApplicationEventPublisher applicationEventPublisher) {
    this.menuFacade = menuFacade;
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

    menuFacade.save(menu);

    applicationEventPublisher.publish(new MenuCreatedEvent(command, id));

    return Optional.of(id);
  }

  protected void verifyMenuNotAlreadyExisting(CreateMenuCommand command) {
    Page<Menu> existing =
            menuFacade.findAllByRestaurantIdAndName(
            command.getRestaurantId().toString(), command.getName(), PageRequest.of(0, 1));
    if (!existing.getContent().isEmpty()
        && existing.get().anyMatch(m -> m.getName().equals(command.getName()))) {
      throw new MenuAlreadyExistsException(command, command.getRestaurantId(), command.getName());
    }
  }
}
