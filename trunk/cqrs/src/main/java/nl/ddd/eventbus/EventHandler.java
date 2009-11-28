package nl.ddd.eventbus;

import nl.ddd.eventstorage.Event;

public interface EventHandler<T extends Event> {

    void handle(T event);
}
