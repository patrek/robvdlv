package nl.ddd.commandhandler;

import javax.annotation.Resource;

import nl.ddd.command.AddBookCommand;
import nl.ddd.domain.Catalogue;
import nl.ddd.eventbus.EventBus;
import nl.ddd.eventstorage.EventStorage;
import nl.ddd.repository.CatalogueRepository;

import static nl.ddd.domain.BookBuilder.aBook;

public class AddbookCommandHandler implements CommandHandler<AddBookCommand> {

    @Resource
    private CatalogueRepository repository;

    @Resource
    private EventBus eventBus;

    @Resource
    private EventStorage eventStorage;

    public void execute(AddBookCommand command) {
        Catalogue catalogue = repository.findCatalogue();
        catalogue.addBook(aBook()
                .withIsbn(command.getIsbn())
                .withTitle(command.getTitle())
                .withAuthor(command.getAuthor())
                .build());

        publishEvents(catalogue);
    }

    private void publishEvents(Catalogue catalogue) {
        eventBus.publishEvents(catalogue.getChanges());

        eventStorage.saveEventsForEventProvider(catalogue);
    }
}
