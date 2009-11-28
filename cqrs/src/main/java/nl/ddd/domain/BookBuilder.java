package nl.ddd.domain;

/**
 * ?
 *
 * @author Rob van der Linden Vooren
 */
public class BookBuilder {

    private String isbn;
    private String title;
    private String author;

    public static BookBuilder aBook() {
        return new BookBuilder();
    }

    public BookBuilder withIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public BookBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public BookBuilder withAuthor(String author) {
        this.author = author;
        return this;
    }

    public Book build() {
        return new Book(new Isbn(isbn), new Title(title), new Author(author));
    }
}
