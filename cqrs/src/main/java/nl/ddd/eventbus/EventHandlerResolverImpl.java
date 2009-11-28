package nl.ddd.eventbus;

import nl.ddd.eventstorage.Event;
import org.apache.commons.lang.Validate;

import static java.lang.String.*;
import java.util.Map;

public class EventHandlerResolverImpl implements EventHandlerResolver {

    private Map<Event, EventHandler> eventsToHandlers;

    public EventHandlerResolverImpl(Map<Event, EventHandler> eventsToHandlers) {
        Validate.notNull(eventsToHandlers, "EventsToHandlers must not be null");
        this.eventsToHandlers = eventsToHandlers;
    }

    public EventHandler resolveEventHandler(Event event) {
        EventHandler eventHandler = eventsToHandlers.get(event);
        Validate.notNull(eventHandler, format("No EventHandler registered for event '%s'", event));
        return eventHandler;
    }
}
