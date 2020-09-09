package com.xxAMIDOxx.menu.handlers;

import com.xxAMIDOxx.core.messaging.publish.ApplicationEventPublisher;
import com.xxAMIDOxx.menu.commands.UpdateItemCommand;
import com.xxAMIDOxx.menu.domain.Category;
import com.xxAMIDOxx.menu.domain.Item;
import com.xxAMIDOxx.menu.domain.Menu;
import com.xxAMIDOxx.menu.events.CategoryUpdatedEvent;
import com.xxAMIDOxx.menu.events.MenuEvent;
import com.xxAMIDOxx.menu.events.MenuItemUpdatedEvent;
import com.xxAMIDOxx.menu.events.MenuUpdatedEvent;
import com.xxAMIDOxx.menu.exception.CategoryDoesNotExistException;
import com.xxAMIDOxx.menu.exception.ItemAlreadyExistsException;
import com.xxAMIDOxx.menu.exception.ItemDoesNotExistsException;
import com.xxAMIDOxx.menu.repository.AzureMenuRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

/** @author ArathyKrishna */
@Component
public class UpdateItemHandler extends MenuBaseCommandHandler<UpdateItemCommand> {

  public UpdateItemHandler(AzureMenuRepository repository, ApplicationEventPublisher publisher) {
    super(repository, publisher);
  }

  @Override
  Optional<UUID> handleCommand(Menu menu, UpdateItemCommand command) {
    Category category = getCategory(menu, command);
    Item updated = updateItem(command, category);
    menu.addOrUpdateCategory(category.addOrUpdateItem(updated));
    menuRepository.save(menu);
    return Optional.of(command.getItemId());
  }

  /**
   * If the request is to update the description/available/price of an existing item then allow that
   * if the request is to update an item but an item with that name already exists then throw an
   * exception if there are no item with the same name then allow that
   *
   * @param command update item request
   * @param category category
   * @return item
   */
  Item updateItem(UpdateItemCommand command, Category category) {
    Item item = getItem(category, command);

    category
        .getItems()
        .forEach(
            t -> {
              if (t.getName().equalsIgnoreCase(command.getName())) {
                if (t.getId().equalsIgnoreCase(command.getItemId().toString())) {
                  item.setAvailable(command.getAvailable());
                  item.setDescription(command.getDescription());
                  item.setPrice(command.getPrice());
                } else {
                  throw new ItemAlreadyExistsException(
                      command, command.getCategoryId(), command.getName());
                }
              } else {
                item.setAvailable(command.getAvailable());
                item.setDescription(command.getDescription());
                item.setName(command.getName());
                item.setPrice(command.getPrice());
              }
            });
    return item;
  }

  @Override
  List<MenuEvent> raiseApplicationEvents(Menu menu, UpdateItemCommand command) {
    return Arrays.asList(
        new MenuItemUpdatedEvent(command, command.getCategoryId(), command.getItemId()),
        new CategoryUpdatedEvent(command, command.getCategoryId()),
        new MenuUpdatedEvent(command));
  }

  Category getCategory(Menu menu, UpdateItemCommand command) {
    return findCategory(menu, command.getCategoryId())
        .orElseThrow(() -> new CategoryDoesNotExistException(command, command.getCategoryId()));
  }

  Item getItem(Category category, UpdateItemCommand command) {
    return findItem(category, command.getItemId())
        .orElseThrow(
            () ->
                new ItemDoesNotExistsException(
                    command, command.getCategoryId(), command.getItemId()));
  }
}
