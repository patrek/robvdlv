package nl.ddd.eventstorage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MockEventProvider implements EventProvider {

    private final UUID uuid;
    private final List<Event> events;

    public MockEventProvider() {
        this.uuid = UUID.randomUUID();
        this.events = new ArrayList<Event>();
    }

    public UUID getUUID() {
        return uuid;
    }

    public List<Event> getChanges() {
        return events;
    }

    public void addEvent(Event event) {
        events.add(event);
    }
}
