package nl.ddd.eventbus;

import java.util.List;

import nl.ddd.eventstorage.Event;

/**
 * @author Erik Pragt
 */
public interface EventBus {
    void publishEvents(List<Event> changes);
}
