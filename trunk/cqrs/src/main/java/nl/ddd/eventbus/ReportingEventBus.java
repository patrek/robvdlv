package nl.ddd.eventbus;

import java.util.List;

import javax.annotation.Resource;

import nl.ddd.eventstorage.Event;

/**
 * @author Erik Pragt
 */
public class ReportingEventBus implements EventBus {

    @Resource
    private EventHandlerResolver eventHandlerResolver;

    @Override
    public void publishEvents(List<Event> changes) {
        for (Event event : changes) {
            EventHandler handler = eventHandlerResolver.resolveEventHandler(event.getClass());
            handler.handle(event);
        }
    }
}
