package com.xxAMIDOxx.menu.exception;

import com.xxAMIDOxx.menu.commands.MenuCommand;
import com.xxAMIDOxx.menu.commands.OperationCode;

public class MenuApiException extends ApiException {

  public MenuApiException(String message, ExceptionCode exceptionCode, MenuCommand menuCommand) {
    super(
        message,
        exceptionCode,
        OperationCode.fromCode(menuCommand.getOperationCode()),
        menuCommand.getCorrelationId());
  }
}
