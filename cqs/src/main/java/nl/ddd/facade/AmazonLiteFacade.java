package nl.ddd.facade;

import nl.ddd.command.Command;

/**
 * Facade providing functinality for our AmazonLite application, yay.
 *
 * @author Rob van der Linden Vooren
 */
public interface AmazonLiteFacade {

    /**
     * Publish the command.
     *
     * @param command the command to be publised
     */
    void publish(Command command);
}
