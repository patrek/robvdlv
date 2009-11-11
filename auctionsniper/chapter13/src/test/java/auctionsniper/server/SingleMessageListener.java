package auctionsniper.server;

import static org.hamcrest.CoreMatchers.notNullValue;
import org.hamcrest.Matcher;
import static org.hamcrest.Matchers.is;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;
import static org.junit.Assert.assertThat;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * A message listener that is capable of processing only one message at a time.
 *
 * @author Rob van der Linden Vooren
 */
class SingleMessageListener implements MessageListener {

    private final BlockingQueue<Message> messages = new ArrayBlockingQueue<Message>(1);

    @Override
    public void processMessage(Chat chat, Message message) {
        messages.add(message);
    }

    /**
     * Checks that the Listener has received the expected message within the timeout period.
     */
    public void receivesAMessage(Matcher<String> messageMatcher) throws InterruptedException {
        final Message message = messages.poll(5, SECONDS);
        assertThat("Message", message, is(notNullValue()));
        assertThat(message.getBody(), messageMatcher);
    }
}
