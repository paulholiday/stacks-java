package com.xxAMIDOxx.xxSTACKSxx.menu.commands;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Command for deleting Menu
 *
 * @author ArathyKrishna
 */
@Getter
@Setter
public class DeleteMenuCommand extends MenuCommand {

    public DeleteMenuCommand(
            OperationCode operationCode, String correlationId,
            UUID menuId) {
        super(OperationCode.DELETE_MENU, correlationId, menuId);
    }
}
