package nl.ddd.eventstorage;

import java.util.List;
import java.util.UUID;

public interface EventStorage<T> {

    List<T> getEventsForEventProvider(UUID uuid);

    void saveEventsForEventProvider(EventProvider eventProvider);

}
