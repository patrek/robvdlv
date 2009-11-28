package nl.ddd.eventstorage;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/context/applicationContext.xml", "classpath:/context/testContext.xml"})
@Transactional
public class MySQLEventStorageTest {

    @Resource
    private MySQLEventStorage eventStorage;

    private final MockEventProvider eventProvider = new MockEventProvider();

    @Test
    public void savesEventsForEventProvider() {
        eventProvider.addEvent(new MySerializible("data to serialize"));

        eventStorage.saveEventsForEventProvider(eventProvider);

        List<Event> events = eventStorage.getEventsForEventProvider(eventProvider.getUUID());
        assertEquals(1, events.size());
        MySerializible data = (MySerializible) events.get(0);
        assertEquals("data to serialize", data.getMessage());

        eventStorage.saveEventsForEventProvider(eventProvider);

        events = eventStorage.getEventsForEventProvider(eventProvider.getUUID());
        assertEquals(2, events.size());
        data = (MySerializible) events.get(1);
        assertEquals("data to serialize", data.getMessage());
    }

    private static class MySerializible extends Event implements Serializable {

        private final String message;

        public MySerializible(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
