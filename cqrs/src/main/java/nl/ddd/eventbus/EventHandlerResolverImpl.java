package nl.ddd.eventbus;

import java.util.Map;

import org.apache.commons.lang.Validate;

import static java.lang.String.format;

public class EventHandlerResolverImpl implements EventHandlerResolver<Class> {

    private Map<Class, EventHandler> eventsToHandlers;

    public EventHandlerResolverImpl(Map<Class, EventHandler> eventsToHandlers) {
        Validate.notNull(eventsToHandlers, "EventsToHandlers must not be null");
        this.eventsToHandlers = eventsToHandlers;
    }

    public EventHandler resolveEventHandler(Class event) {
        EventHandler eventHandler = eventsToHandlers.get(event);
        Validate.notNull(eventHandler, format("No EventHandler registered for event '%s'", event));
        return eventHandler;
    }
}
