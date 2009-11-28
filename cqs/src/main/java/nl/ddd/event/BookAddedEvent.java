package nl.ddd.event;

import nl.ddd.eventstorage.Event;

/**
 * @author Erik Pragt
 */
public class BookAddedEvent extends Event {

    private String author;
    private String isbn;
    private String title;

    public BookAddedEvent(String author, String isbn, String title) {
        this.author = author;
        this.isbn = isbn;
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }
}
