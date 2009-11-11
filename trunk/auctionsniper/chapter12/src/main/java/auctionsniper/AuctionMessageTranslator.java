package auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * Translates messages from the Auction in terms of the application.
 *
 * @author Rob van der Linden Vooren
 */
public class AuctionMessageTranslator implements MessageListener {

    /*
        Class might not be as clear as it could be about what it is doing, mixing parsing and dispatching activies.
     */

    private final AuctionEventListener listener;

    public AuctionMessageTranslator(AuctionEventListener listener) {
        this.listener = listener;
    }

    @Override
    public void processMessage(Chat chat, Message message) {
        final Map<String, String> event = unpackEventFrom(message);

        String type = event.get("Event");
        if ("CLOSE".equals(type)) {
            listener.auctionClosed();
        } else if ("PRICE".equals(type)) {
            listener.currentPrice(
                    Integer.parseInt(event.get("CurrentPrice")),
                    Integer.parseInt(event.get("Increment"))
            );
        }
    }

    private Map<String, String> unpackEventFrom(Message message) {
        Map<String, String> event = new HashMap<String, String>();
        for (String element : message.getBody().split(";")) {
            String[] pair = element.split(":");
            event.put(pair[0].trim(), pair[1].trim());
        }
        return event;
    }
}
