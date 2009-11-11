package auctionsniper.server;

import auctionsniper.Main;
import static auctionsniper.Main.AUCTION_RESOURCE;
import static auctionsniper.Main.ITEM_ID_AS_LOGIN;
import static org.hamcrest.CoreMatchers.equalTo;
import org.hamcrest.Matcher;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import static org.junit.Assert.assertThat;

import static java.lang.String.format;

/**
 * Fake AuctionServer implementation that allows tests to check how the Auction Sniper application interacts with an
 * auction using XMPP messages. It has three responsibilities: <ul> <li>it must connect to the XMPP broker and accept a
 * request to join the chat from the Sniper</li> <li>it must receive chat messages from the Sniper or fail if no message
 * arrives within some timeout; and,</li><li> it must allow the test to send messages back to the Sniper as specified by
 * Southabee's On-Line</li> </ul>
 *
 * @author Rob van der Linden Vooren
 */
public class FakeAuctionServer {

    public static final String XMPP_HOSTNAME = "localhost";
    private static final String AUCTION_PASSWORD = "auction";

    private final String itemId;
    private final XMPPConnection connection;
    private Chat currentChat;

    private final SingleMessageListener messageListener = new SingleMessageListener();

    public FakeAuctionServer(String itemId) {
        this.itemId = itemId;
        this.connection = new XMPPConnection(XMPP_HOSTNAME);
    }

    public void startSellingItem() throws XMPPException {
        connection.connect();
        connection.login(format(ITEM_ID_AS_LOGIN, itemId), AUCTION_PASSWORD, AUCTION_RESOURCE);
        connection.getChatManager().addChatListener(new ChatManagerListener() {
            /**
             * This FakeAuctionServer just exists to support testing. As such it has no need to juggle multiple
             * chats as a real server would; one will suffice.
             */
            @Override
            public void chatCreated(Chat chat, boolean createdLocally) {
                currentChat = chat;
                chat.addMessageListener(messageListener);
            }
        });
    }

    /**
     * The test needs to know when a Join message has arrived. We just check whether any message has arrived, since the
     * Sniper will only be sending Join messages to start with. This implementation will fail if no message is received
     * within 5 seconds.
     */
    public void hasReceivedJoinRequestFromSniper(String sniperId) throws InterruptedException {
        receivesAMessageMatching(sniperId, equalTo(Main.JOIN_COMMAND_FORMAT));
    }

    /**
     * The test needs to be able to simulate the acution announcing when it closes, which is why we held onto the
     * currentChat when it opened. As with the Jion request, the fake auction just sends an empty message, since this is
     * the only event we support so far.
     */
    public void announceClosed() throws XMPPException {
        currentChat.sendMessage(Main.CLOSE_COMMAND_FORMAT);
    }

    public String getItemId() {
        return itemId;
    }

    /**
     * Closes the XMMPConnection.
     */
    public void stop() {
        connection.disconnect();
    }

    public void reportPrice(int price, int increment, String bidder) throws XMPPException {
        currentChat.sendMessage(format(Main.PRICE_ANNOUNCE_FORMAT, price, increment, bidder));
    }

    public void hasReceivedBid(int bid, String sniperId) throws InterruptedException {
        receivesAMessageMatching(sniperId, equalTo(format(Main.BID_COMMAND_FORMAT, bid)));
    }

    private void receivesAMessageMatching(String sniperId, Matcher<String> messageMatcher) throws InterruptedException {
        messageListener.receivesAMessage(messageMatcher);
        // Notice that sniperId is checked *after* message contents is checked. This means that the server must have
        // accepted a connection and set up currentChat, otherwise the test would fail by checking the sniperId
        // prematurely.
        assertThat(currentChat.getParticipant(), equalTo(sniperId));
    }
}
