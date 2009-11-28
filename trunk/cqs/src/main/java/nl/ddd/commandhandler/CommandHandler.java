package nl.ddd.commandhandler;

import nl.ddd.command.Command;

/**
 * Handles {@link Command}s.
 *
 * @param <T>
 */
public interface CommandHandler<T> {

    void execute(T command);
}
