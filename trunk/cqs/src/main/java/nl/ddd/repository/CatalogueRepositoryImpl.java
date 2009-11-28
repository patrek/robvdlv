package nl.ddd.repository;

import nl.ddd.domain.Catalogue;
import nl.ddd.eventstorage.Event;
import nl.ddd.eventstorage.EventStorage;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

public class CatalogueRepositoryImpl implements CatalogueRepository {

    private static final UUID CATALOGUE_UUID = UUID.fromString("00577889-c450-40f3-b15d-ac8133689ccb");

    @Resource
    private EventStorage<Event> eventStorage;

    public Catalogue findCatalogue() {
        Catalogue catalogue = new Catalogue(CATALOGUE_UUID);
        List<Event> events = eventStorage.getEventsForEventProvider(catalogue.getUUID());
        catalogue.loadFromEventHistory(events);
        return catalogue;
    }

    public void save(Catalogue catalogue) {
        eventStorage.saveEventsForEventProvider(catalogue);
    }
}
