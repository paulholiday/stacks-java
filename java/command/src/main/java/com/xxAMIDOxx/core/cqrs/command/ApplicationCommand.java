package com.xxAMIDOxx.core.cqrs.command;

import com.xxAMIDOxx.core.operations.OperationContext;

public abstract class ApplicationCommand extends OperationContext {
  public ApplicationCommand(int operationCode, String correlationId) {
    super(operationCode, correlationId);
  }
}
