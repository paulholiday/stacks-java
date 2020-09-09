package com.xxAMIDOxx.menu.events;

import com.xxAMIDOxx.menu.commands.MenuCommand;

/** @author ArathyKrishna */
public class MenuDeletedEvent extends MenuEvent {

  public MenuDeletedEvent(MenuCommand command) {
    super(command, EventCode.MENU_DELETED);
  }
}
