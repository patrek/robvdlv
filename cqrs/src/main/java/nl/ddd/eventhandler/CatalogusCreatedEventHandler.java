package nl.ddd.eventhandler;

import javax.annotation.Resource;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import nl.ddd.event.CatalogusCreatedEvent;
import nl.ddd.eventbus.EventHandler;

public class CatalogusCreatedEventHandler implements EventHandler<CatalogusCreatedEvent> {

    @Resource
    private SimpleJdbcTemplate jdbcTemplate;

    @Override
    public void handle(CatalogusCreatedEvent event) {
        jdbcTemplate.update("insert into catalogs(uuid) values (?)", event);
    }
}
