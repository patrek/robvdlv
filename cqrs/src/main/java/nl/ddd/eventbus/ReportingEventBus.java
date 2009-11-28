package nl.ddd.eventbus;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import nl.ddd.eventstorage.Event;

/**
 * @author Erik Pragt
 */
public class ReportingEventBus implements EventBus {

    @Resource
    private Map<Event, EventHandler> eventHandlers;

    @Override
    public void publishEvents(List<Event> changes) {
    }
}
