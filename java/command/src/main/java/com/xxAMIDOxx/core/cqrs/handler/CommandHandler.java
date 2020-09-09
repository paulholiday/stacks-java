package com.xxAMIDOxx.core.cqrs.handler;

import com.xxAMIDOxx.core.cqrs.command.ApplicationCommand;
import java.util.Optional;
import java.util.UUID;

public interface CommandHandler<T extends ApplicationCommand> {
  Optional<UUID> handle(T command);
}
