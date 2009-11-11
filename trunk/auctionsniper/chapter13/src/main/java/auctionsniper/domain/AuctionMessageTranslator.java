package auctionsniper.domain;

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
        AuctionEvent event = AuctionEvent.from(message.getBody());

        String type = event.type();
        if ("CLOSE".equals(type)) {
            listener.auctionClosed();
        } else if ("PRICE".equals(type)) {
            listener.currentPrice(
                    Integer.parseInt(event.currentPrice()),
                    Integer.parseInt(event.increment())
            );
        }
    }

    private static class AuctionEvent {

        /*
           Package up common types, such as collections in classes that express in domain language.
           Try to use the language of the problem that is worked on, rather than the programming language.

           Rule of thumb: limit passing around types with generics. It is a hint that there's a domain concept that
           should be extracted in a type.
        */

        private final Map<String, String> fields = new HashMap<String, String>();

        public String type() {
            return fields.get("Event");
        }

        public String currentPrice() {
            return fields.get("CurrentPrice");
        }

        public String increment() {
            return fields.get("Increment");
        }

        private void addField(String field) {
            String[] pair = field.split(":");
            fields.put(pair[0].trim(), pair[1].trim());
        }

        static String[] fieldsIn(String messageBody) {
            return messageBody.split(";");
        }

        static AuctionEvent from(String messageBody) {
            AuctionEvent event = new AuctionEvent();
            for (String field : fieldsIn(messageBody)) {
                event.addField(field);
            }
            return event;
        }
    }
}
