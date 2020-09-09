package com.xxAMIDOxx.menu.events;

import com.xxAMIDOxx.menu.commands.MenuCommand;
import java.util.UUID;

public class MenuItemCreatedEvent extends MenuItemEvent {
  public MenuItemCreatedEvent(MenuCommand command, UUID categoryId, UUID itemId) {
    super(command, EventCode.MENU_ITEM_CREATED, categoryId, itemId);
  }
}
