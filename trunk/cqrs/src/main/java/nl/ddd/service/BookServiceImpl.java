package nl.ddd.service;

import nl.ddd.domain.Author;
import nl.ddd.domain.Book;
import nl.ddd.domain.Isbn;
import nl.ddd.domain.Title;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Erik Pragt
 */
public class BookServiceImpl implements BookService {

    @Resource
    private SimpleJdbcTemplate jdbcTemplate;

    public List<Book> listAllBooks() {
        return jdbcTemplate.query("select * from books", new ParameterizedRowMapper<Book>() {
            public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Book(new Isbn(rs.getString("isbn")), new Title(rs.getString("title")), new Author(rs.getString("author")));
            }
        });
    }

    public void addBook(Book book) {
        jdbcTemplate.update("insert into books (isbn,title,author) values (?,?,?)", book.getIsbn().getName(), book.getTitle().getName(), book.getAuthor().getName());
    }
}
