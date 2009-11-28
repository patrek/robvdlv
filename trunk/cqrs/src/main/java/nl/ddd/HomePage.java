package nl.ddd;

import nl.ddd.command.AddBookCommand;
import nl.ddd.facade.AmazonLiteFacade;
import nl.ddd.service.BookService;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * Homepage
 */
public class HomePage extends WebPage {

    private static final long serialVersionUID = 1L;

    @SpringBean(name = "bookService")
    private BookService bookService;

    @SpringBean(name = "amazonLiteFacade")
    private AmazonLiteFacade amazonLiteFacade;

    // TODO Add any page properties or variables here

    /**
     * Constructor that is invoked when page is invoked without a session.
     *
     * @param parameters Page parameters
     */
    public HomePage(final PageParameters parameters) {

        // Add the simplest type of label
        add(new Label("message", "If you see this message wicket is properly configured and running"));
        add(new Label("books", String.valueOf(bookService.listAllBooks())));
        add(new BookDtoForm());
    }

    public final class BookDtoForm extends Form {
        private final BookDto book = new BookDto();

        public BookDtoForm() {
            super("addbook");
            add(new TextField<String>("isbn", new PropertyModel<String>(book, "isbn")));
            add(new TextField<String>("title", new PropertyModel<String>(book, "title")));
            add(new TextField<String>("author", new PropertyModel<String>(book, "author")));
        }

        public final void onSubmit() {
            final BookDto newBook = new BookDto();
            BeanUtils.copyProperties(book, newBook);

            amazonLiteFacade.publish(new AddBookCommand(newBook.isbn, newBook.title, newBook.author + System.currentTimeMillis()));
        }
    }

    public class BookDto implements Serializable {

        private String isbn = "isbn";
        private String title = "title";
        private String author = "author";
    }
}
