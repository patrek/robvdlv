package nl.ddd.eventstorage;

import java.util.List;
import java.util.UUID;

/**
 * @author Erik Pragt
 */
public interface EventProvider {

    UUID getUUID();

    List<Event> getChanges();
}
