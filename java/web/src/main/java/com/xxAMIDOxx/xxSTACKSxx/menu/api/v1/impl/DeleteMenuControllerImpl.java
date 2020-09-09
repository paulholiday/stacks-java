package com.xxAMIDOxx.xxSTACKSxx.menu.api.v1.impl;

import static org.springframework.http.HttpStatus.OK;

import com.xxAMIDOxx.xxSTACKSxx.menu.api.v1.DeleteMenuController;
import com.xxAMIDOxx.menu.commands.DeleteMenuCommand;
import com.xxAMIDOxx.menu.handlers.DeleteMenuHandler;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * DeleteMenuController implementation.
 *
 * @author ArathyKrishna
 */
@RestController
public class DeleteMenuControllerImpl implements DeleteMenuController {

  private DeleteMenuHandler handler;

  public DeleteMenuControllerImpl(DeleteMenuHandler handler) {
    this.handler = handler;
  }

  @Override
  public ResponseEntity<Void> deleteMenu(UUID menuId, String correlationId) {

    handler.handle(new DeleteMenuCommand(correlationId, menuId));
    return new ResponseEntity<>(OK);
  }
}
