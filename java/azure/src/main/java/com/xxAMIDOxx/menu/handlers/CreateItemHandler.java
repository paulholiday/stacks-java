package com.xxAMIDOxx.menu.handlers;

import com.xxAMIDOxx.menu.repository.AzureMenuRepository;
import com.xxAMIDOxx.core.messaging.publish.ApplicationEventPublisher;
import com.xxAMIDOxx.menu.commands.CreateItemCommand;
import com.xxAMIDOxx.menu.domain.Category;
import com.xxAMIDOxx.menu.domain.Item;
import com.xxAMIDOxx.menu.domain.Menu;
import com.xxAMIDOxx.menu.events.CategoryUpdatedEvent;
import com.xxAMIDOxx.menu.events.MenuEvent;
import com.xxAMIDOxx.menu.events.MenuItemCreatedEvent;
import com.xxAMIDOxx.menu.events.MenuUpdatedEvent;
import com.xxAMIDOxx.menu.exception.CategoryDoesNotExistException;
import com.xxAMIDOxx.menu.exception.ItemAlreadyExistsException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CreateItemHandler extends MenuBaseCommandHandler<CreateItemCommand> {

  private UUID itemId;

  public CreateItemHandler(
    AzureMenuRepository menuRepository, ApplicationEventPublisher applicationEventPublisher) {
    super(menuRepository, applicationEventPublisher);
  }

  @Override
  Optional<UUID> handleCommand(Menu menu, CreateItemCommand command) {
    itemId = UUID.randomUUID();
    Category category = addItem(getCategory(menu, command), command);
    menuRepository.save(menu.addOrUpdateCategory(category));
    return Optional.of(itemId);
  }

  @Override
  List<MenuEvent> raiseApplicationEvents(Menu menu, CreateItemCommand command) {
    return Arrays.asList(
        new MenuUpdatedEvent(command),
        new CategoryUpdatedEvent(command, command.getCategoryId()),
        new MenuItemCreatedEvent(command, command.getCategoryId(), itemId));
  }

  Category getCategory(Menu menu, CreateItemCommand command) {
    Optional<Category> existing = Optional.empty();

    if (menu.getCategories() != null && !menu.getCategories().isEmpty()) {
      existing =
          menu.getCategories().stream()
              .filter(c -> c.getId().equals(command.getCategoryId().toString()))
              .findFirst();
    }
    return existing.orElseThrow(
        () -> new CategoryDoesNotExistException(command, command.getCategoryId()));
  }

  Category addItem(Category category, CreateItemCommand command) {

    itemId = UUID.randomUUID();
    List<Item> items = category.getItems() == null ? new ArrayList<>() : category.getItems();

    if (items.stream().anyMatch(c -> c.getName().equalsIgnoreCase(command.getName()))) {
      throw new ItemAlreadyExistsException(command, command.getCategoryId(), command.getName());
    } else {
      Item item =
          new Item(
              itemId.toString(),
              command.getName(),
              command.getDescription(),
              command.getPrice(),
              command.getAvailable());
      category.getItems().add(item);
      return category;
    }
  }
}
