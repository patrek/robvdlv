package nl.ddd.domain;

import nl.ddd.eventstorage.Event;
import nl.ddd.eventstorage.EventProvider;

import java.util.List;

public interface AggregateRoot extends EventProvider {

    void loadFromEventHistory(List<Event> events);

    void clearEventHistory();

}
