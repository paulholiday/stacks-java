package com.xxAMIDOxx.menu.exception;


import com.xxAMIDOxx.menu.commands.MenuCommand;

public class CategoryAlreadyExistsException extends MenuApiException {

  public CategoryAlreadyExistsException(MenuCommand command, String name) {
    super(
        String.format(
            "A category with the name '%s' already exists for the menu with id '%s'.",
            name, command.getMenuId()),
        ExceptionCode.CATEGORY_ALREADY_EXISTS,
        command);
  }
}
