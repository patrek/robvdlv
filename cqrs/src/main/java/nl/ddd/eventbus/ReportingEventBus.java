package nl.ddd.eventbus;

import nl.ddd.eventstorage.Event;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Erik Pragt
 */
public class ReportingEventBus implements EventBus {

    @Resource
    private EventHandlerResolver eventHandlerResolver;

    @Override
    public void publishEvents(List<Event> changes) {
        for (Event event : changes) {
            EventHandler handler = eventHandlerResolver.resolveEventHandler(event);
            handler.handle(event);
        }
    }
}
