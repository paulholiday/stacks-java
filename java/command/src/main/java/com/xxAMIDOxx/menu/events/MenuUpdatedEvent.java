package com.xxAMIDOxx.menu.events;

import com.xxAMIDOxx.menu.commands.MenuCommand;

public class MenuUpdatedEvent extends MenuEvent {

  public MenuUpdatedEvent(MenuCommand command) {
    super(command, EventCode.MENU_UPDATED);
  }
}
