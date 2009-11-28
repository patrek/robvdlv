package nl.ddd.eventbus;

import nl.ddd.eventstorage.Event;

import java.util.List;

/**
 * @author Erik Pragt
 */
public interface EventBus {

    void publishEvents(List<Event> changes);
}
