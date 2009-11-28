package nl.ddd.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import nl.ddd.event.BookAddedEvent;
import nl.ddd.event.CatalogusCreatedEvent;
import nl.ddd.eventstorage.Event;

import static nl.ddd.domain.BookBuilder.aBook;

public class Catalogue implements AggregateRoot {

    private final List<Event> events = new ArrayList<Event>();
    private final List<Book> catalogus = new ArrayList<Book>();
    private final UUID uuid;

    public Catalogue(UUID uuid) {
        this.uuid = uuid;
        events.add(new CatalogusCreatedEvent(uuid));
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public List<Event> getChanges() {
        return Collections.unmodifiableList(events);
    }

    @Override
    public void loadFromEventHistory(List<Event> events) {
        for (Event event : events) {
            if (event instanceof BookAddedEvent) {
                onBookAddedEvent((BookAddedEvent) event);
            }
        }
    }

    @Override
    public void clearEventHistory() {
        events.clear();
    }

    public void loadFromEventHistory(Event... events) {
        loadFromEventHistory(Arrays.asList(events));
    }

    public void addBook(Book book) {
        BookAddedEvent event = new BookAddedEvent(book.getIsbn().getName(), book.getTitle().getName(), book.getAuthor().getName());
        events.add(event);
        onBookAddedEvent(event);
    }

    private void onBookAddedEvent(BookAddedEvent event) {
        catalogus.add(aBook()
                .withIsbn(event.getIsbn())
                .withTitle(event.getTitle())
                .withAuthor(event.getAuthor())
                .build()
        );
    }
}
