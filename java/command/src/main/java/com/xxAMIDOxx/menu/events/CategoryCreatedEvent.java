package com.xxAMIDOxx.menu.events;

import com.xxAMIDOxx.menu.commands.MenuCommand;

import java.util.UUID;

public class CategoryCreatedEvent extends CategoryEvent {

  public CategoryCreatedEvent(MenuCommand command, UUID categoryId) {
    super(command, EventCode.CATEGORY_CREATED, categoryId);
  }
}
