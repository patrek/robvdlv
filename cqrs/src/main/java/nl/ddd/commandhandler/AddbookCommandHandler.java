package nl.ddd.commandhandler;

import nl.ddd.command.AddBookCommand;
import static nl.ddd.domain.BookBuilder.*;
import nl.ddd.domain.Catalogue;
import nl.ddd.eventbus.EventBus;
import nl.ddd.eventstorage.EventStorage;
import nl.ddd.repository.CatalogueRepository;

import javax.annotation.Resource;

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
        eventStorage.saveEventsForEventProvider(catalogue);
        eventBus.publishEvents(catalogue.getChanges());
    }
}
