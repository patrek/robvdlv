package nl.ddd.repository;

import nl.ddd.domain.Catalogue;

/**
 * @author Erik Pragt
 */
public interface CatalogueRepository {

    Catalogue findCatalogue();

    void save(Catalogue catalogue);

}
