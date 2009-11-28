package nl.ddd.domain;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @author Erik Pragt
 */
public class Book implements Serializable {

    private Isbn isbn;
    private Title title;
    private Author author;

    public Book() {
    }

    public Book(Isbn isbn, Title title, Author author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    public Isbn getIsbn() {
        return isbn;
    }

    public Title getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }
}
