package nl.ddd.eventstorage;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import javax.annotation.Resource;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MySQLEventStorage implements EventStorage {

    @Resource
    private SimpleJdbcTemplate jdbcTemplate;

    public List<Event> getEventsForEventProvider(UUID uuid) {
        ParameterizedRowMapper<Event> rowMapper = new ParameterizedRowMapper<Event>() {
            @Override
            public Event mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
                InputStream inputStream = resultSet.getBinaryStream("data");
                return (Event) SerializationUtils.deserialize(inputStream);
            }
        };
        return jdbcTemplate.query("select data from event_events where eventprovider_id = ? order by sequence", rowMapper, uuid.toString());
    }

    public void saveEventsForEventProvider(EventProvider eventProvider) {
        if (!isKnownEventProvider(eventProvider)) {
            insertEventProvider(eventProvider);
        }
        for (Event event : eventProvider.getChanges()) {
            insertEventRecord(eventProvider, event);
        }
    }

    boolean isKnownEventProvider(EventProvider eventProvider) {
        String eventProviderUuid = eventProvider.getUUID().toString();
        int count = jdbcTemplate.queryForInt("select count(*) from event_eventproviders where id = ?", eventProviderUuid);
        return count > 0;
    }

    void insertEventRecord(EventProvider eventProvider, Event event) {
        String eventProviderUuid = eventProvider.getUUID().toString();
        Date occurredAt = new Date();
        jdbcTemplate.update("insert into event_events (occurred_at, eventprovider_id, data) values (?,?,?)", occurredAt, eventProviderUuid, event);
    }

    void insertEventProvider(EventProvider eventProvider) {
        String uuid = eventProvider.getUUID().toString();
        String simpleClassName = eventProvider.getClass().getSimpleName();
        jdbcTemplate.update("insert into event_eventproviders (id,version,type) values (?,?,?)", uuid, 0, simpleClassName);
    }
}
