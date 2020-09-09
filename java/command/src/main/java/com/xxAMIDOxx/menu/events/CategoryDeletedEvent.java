package com.xxAMIDOxx.menu.events;

import com.xxAMIDOxx.menu.commands.MenuCommand;
import java.util.UUID;

/** @author ArathyKrishna */
public class CategoryDeletedEvent extends CategoryEvent {

  public CategoryDeletedEvent(MenuCommand command, UUID categoryId) {
    super(command, EventCode.CATEGORY_DELETED, categoryId);
  }
}
