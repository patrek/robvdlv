package nl.ddd.service;

import java.util.List;

import nl.ddd.domain.Book;

/**
 * @author Erik Pragt
 */
public interface BookService {

    List<Book> listAllBooks();

    void addBook(Book book);
}
