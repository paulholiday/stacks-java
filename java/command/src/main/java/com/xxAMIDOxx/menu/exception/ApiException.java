package com.xxAMIDOxx.menu.exception;


import com.xxAMIDOxx.menu.commands.OperationCode;

public class ApiException extends RuntimeException {

  ExceptionCode exceptionCode;
  OperationCode operationCode;
  String correlationId;

  public ApiException(
      String message,
      ExceptionCode exceptionCode,
      OperationCode operationCode,
      String correlationId) {
    super(message);
    this.exceptionCode = exceptionCode;
    this.operationCode = operationCode;
    this.correlationId = correlationId;
  }

  public ExceptionCode getExceptionCode() {
    return exceptionCode;
  }

  public OperationCode getOperationCode() {
    return operationCode;
  }

  public String getCorrelationId() {
    return correlationId;
  }
}
