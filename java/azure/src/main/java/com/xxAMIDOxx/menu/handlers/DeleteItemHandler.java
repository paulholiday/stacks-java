package com.xxAMIDOxx.menu.handlers;

import com.xxAMIDOxx.core.messaging.publish.ApplicationEventPublisher;
import com.xxAMIDOxx.menu.commands.DeleteItemCommand;
import com.xxAMIDOxx.menu.domain.Category;
import com.xxAMIDOxx.menu.domain.Item;
import com.xxAMIDOxx.menu.domain.Menu;
import com.xxAMIDOxx.menu.events.CategoryUpdatedEvent;
import com.xxAMIDOxx.menu.events.ItemDeletedEvent;
import com.xxAMIDOxx.menu.events.MenuEvent;
import com.xxAMIDOxx.menu.events.MenuUpdatedEvent;
import com.xxAMIDOxx.menu.exception.CategoryDoesNotExistException;
import com.xxAMIDOxx.menu.exception.ItemDoesNotExistsException;
import com.xxAMIDOxx.menu.repository.AzureMenuRepository;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/** @author ArathyKrishna */
@Component
public class DeleteItemHandler extends MenuBaseCommandHandler<DeleteItemCommand> {

  public DeleteItemHandler(
      AzureMenuRepository menuRepository, ApplicationEventPublisher applicationEventPublisher) {
    super(menuRepository, applicationEventPublisher);
  }

  @Override
  Optional<UUID> handleCommand(Menu menu, DeleteItemCommand command) {
    Category category = getCategory(menu, command);
    Item item = getItem(category, command);

    List<Item> itemList =
        category.getItems().stream()
            .filter(t -> !Objects.equals(t, item))
            .collect(Collectors.toList());
    category.setItems(!itemList.isEmpty() ? itemList : Collections.emptyList());

    menuRepository.save(menu.addOrUpdateCategory(category));

    return Optional.empty();
  }

  @Override
  List<MenuEvent> raiseApplicationEvents(Menu menu, DeleteItemCommand command) {

    return Arrays.asList(
        new ItemDeletedEvent(command, command.getCategoryId(), command.getItemId()),
        new CategoryUpdatedEvent(command, command.getCategoryId()),
        new MenuUpdatedEvent(command));
  }

  Category getCategory(Menu menu, DeleteItemCommand command) {
    return findCategory(menu, command.getCategoryId())
        .orElseThrow(() -> new CategoryDoesNotExistException(command, command.getCategoryId()));
  }

  Item getItem(Category category, DeleteItemCommand command) {
    return findItem(category, command.getItemId())
        .orElseThrow(
            () ->
                new ItemDoesNotExistsException(
                    command, command.getCategoryId(), command.getItemId()));
  }
}
