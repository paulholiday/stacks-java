package com.xxAMIDOxx.menu.handlers;

import com.xxAMIDOxx.core.messaging.publish.ApplicationEventPublisher;
import com.xxAMIDOxx.menu.commands.CreateCategoryCommand;
import com.xxAMIDOxx.menu.domain.Category;
import com.xxAMIDOxx.menu.domain.Menu;
import com.xxAMIDOxx.menu.events.CategoryCreatedEvent;
import com.xxAMIDOxx.menu.events.MenuEvent;
import com.xxAMIDOxx.menu.events.MenuUpdatedEvent;
import com.xxAMIDOxx.menu.exception.CategoryAlreadyExistsException;
import com.xxAMIDOxx.menu.repository.AzureMenuRepository;
import java.util.*;
import org.springframework.stereotype.Component;

@Component
public class CreateCategoryHandler extends MenuBaseCommandHandler<CreateCategoryCommand> {

  private UUID categoryId;

  public CreateCategoryHandler(
      AzureMenuRepository menuRepository, ApplicationEventPublisher applicationEventPublisher) {
    super(menuRepository, applicationEventPublisher);
  }

  @Override
  Optional<UUID> handleCommand(Menu menu, CreateCategoryCommand command) {
    menu.setCategories(addCategory(menu, command));
    menuRepository.save(menu);
    return Optional.of(categoryId);
  }

  @Override
  List<MenuEvent> raiseApplicationEvents(Menu menu, CreateCategoryCommand command) {
    return Arrays.asList(
        new MenuUpdatedEvent(command), new CategoryCreatedEvent(command, categoryId));
  }

  List<Category> addCategory(Menu menu, CreateCategoryCommand command) {
    categoryId = UUID.randomUUID();
    List<Category> categories =
        menu.getCategories() == null ? new ArrayList<>() : menu.getCategories();

    if (categories.stream().anyMatch(c -> c.getName().equalsIgnoreCase(command.getName()))) {
      throw new CategoryAlreadyExistsException(command, command.getName());
    } else {
      categories.add(
          new Category(
              categoryId.toString(),
              command.getName(),
              command.getDescription(),
              new ArrayList<>()));
      return categories;
    }
  }
}
