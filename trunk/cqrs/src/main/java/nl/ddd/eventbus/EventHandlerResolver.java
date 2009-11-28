package nl.ddd.eventbus;

import nl.ddd.eventstorage.Event;

public interface EventHandlerResolver {

    EventHandler resolveEventHandler(Event event);
}
