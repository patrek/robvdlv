package nl.ddd.eventhandler;

import nl.ddd.event.CatalogusCreatedEvent;
import nl.ddd.eventbus.EventHandler;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import javax.annotation.Resource;

public class CatalogusCreatedEventHandler implements EventHandler<CatalogusCreatedEvent> {

    @Resource
    private SimpleJdbcTemplate jdbcTemplate;

    @Override
    public void handle(CatalogusCreatedEvent event) {

    }
}
