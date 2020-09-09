package com.xxAMIDOxx.menu.handlers;

import com.xxAMIDOxx.core.messaging.publish.ApplicationEventPublisher;
import com.xxAMIDOxx.menu.repository.AzureMenuRepository;
import com.xxAMIDOxx.menu.commands.DeleteCategoryCommand;
import com.xxAMIDOxx.menu.domain.Category;
import com.xxAMIDOxx.menu.domain.Menu;
import com.xxAMIDOxx.menu.events.CategoryDeletedEvent;
import com.xxAMIDOxx.menu.events.MenuEvent;
import com.xxAMIDOxx.menu.events.MenuUpdatedEvent;
import com.xxAMIDOxx.menu.exception.CategoryDoesNotExistException;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/** @author ArathyKrishna */
@Component
public class DeleteCategoryHandler extends MenuBaseCommandHandler<DeleteCategoryCommand> {

  public DeleteCategoryHandler(
    AzureMenuRepository menuRepository, ApplicationEventPublisher applicationEventPublisher) {
    super(menuRepository, applicationEventPublisher);
  }

  Optional<UUID> handleCommand(Menu menu, DeleteCategoryCommand command) {
    Category category = getCategory(menu, command);
    List<Category> collect =
        menu.getCategories().stream()
            .filter(t -> !Objects.equals(t, category))
            .collect(Collectors.toList());
    menu.setCategories(!collect.isEmpty() ? collect : Collections.emptyList());
    menuRepository.save(menu);
    return Optional.empty();
  }

  List<MenuEvent> raiseApplicationEvents(Menu menu, DeleteCategoryCommand command) {
    return Arrays.asList(
        new MenuUpdatedEvent(command), new CategoryDeletedEvent(command, command.getCategoryId()));
  }

  Category getCategory(Menu menu, DeleteCategoryCommand command) {
    return findCategory(menu, command.getCategoryId())
        .orElseThrow(() -> new CategoryDoesNotExistException(command, command.getCategoryId()));
  }
}
