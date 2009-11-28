package nl.ddd.command;

import java.io.Serializable;

/**
 * @author Rob van der Linden Vooren
 */
public class AddBookCommand extends Command implements Serializable {

    private final String isbn;
    private final String title;
    private final String author;

    public AddBookCommand(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}
