package nl.ddd.event;

import java.util.UUID;

import nl.ddd.eventstorage.Event;

/**
 * ?
 *
 * @author Rob van der Linden Vooren
 */
public class CatalogusCreatedEvent extends Event {
    private UUID uuid;

    public CatalogusCreatedEvent(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }
}
