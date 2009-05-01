package nl.jteam.samples.support;

import static junit.framework.Assert.assertEquals;
import nl.jteam.samples.domain.Customer;
import nl.jteam.samples.domain.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:/context/applicationContext.xml",
        "classpath:/context/test-localMailServer.xml"
})
public class SimpleOrderManagerIntegrationTest {

    @Resource
    protected SimpleOrderManager simpleOrderManager;

    @Resource
    protected Wiser localMailServer;

    @Test
    public void testPlaceOrder() throws Exception {
        Order order = new Order(1L, new Customer("Jane", "jane@volkswagen.com"));
        simpleOrderManager.placeOrder(order);

        List<WiserMessage> messages = localMailServer.getMessages();
        WiserMessage message = messages.get(0);
        assertEquals(1, messages.size());
        assertEquals("noreply@jteam.nl", message.getEnvelopeSender());
        assertEquals("jane@volkswagen.com", message.getEnvelopeReceiver());
        assertEquals("Your order", message.getMimeMessage().getSubject());
        // etc...
    }
}
