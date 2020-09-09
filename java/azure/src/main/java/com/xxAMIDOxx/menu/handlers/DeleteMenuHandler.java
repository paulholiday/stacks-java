package com.xxAMIDOxx.menu.handlers;

import com.xxAMIDOxx.menu.repository.AzureMenuRepository;
import com.xxAMIDOxx.core.messaging.publish.ApplicationEventPublisher;
import com.xxAMIDOxx.menu.commands.DeleteMenuCommand;
import com.xxAMIDOxx.menu.domain.Menu;
import com.xxAMIDOxx.menu.events.MenuDeletedEvent;
import com.xxAMIDOxx.menu.events.MenuEvent;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Handler for deleting menu
 *
 * @author ArathyKrishna
 */
@Component
public class DeleteMenuHandler extends MenuBaseCommandHandler<DeleteMenuCommand> {

  public DeleteMenuHandler(
    AzureMenuRepository menuRepository, ApplicationEventPublisher applicationEventPublisher) {
    super(menuRepository, applicationEventPublisher);
  }

  @Override
  Optional<UUID> handleCommand(Menu menu, DeleteMenuCommand command) {
    menuRepository.delete(menu);
    return Optional.empty();
  }

  @Override
  List<MenuEvent> raiseApplicationEvents(Menu menu, DeleteMenuCommand command) {
    return Collections.singletonList(new MenuDeletedEvent(command));
  }
}
