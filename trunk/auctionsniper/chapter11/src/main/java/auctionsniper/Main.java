package auctionsniper;

import auctionsniper.ui.MainWindow;
import auctionsniper.ui.SniperStatus;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;

import javax.swing.*;

/**
 * Auction Sniper application entry point.
 *
 * @author Rob van der Linden Vooren
 */
public class Main {

    public static final String ITEM_ID_AS_LOGIN = "auction-%s";
    public static final String AUCTION_RESOURCE = "Auction";
    public static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/" + AUCTION_RESOURCE;

    private static final int ARG_HOSTNAME = 0;
    private static final int ARG_USERNAME = 1;
    private static final int ARG_PASSWORD = 2;
    private static final int ARG_ITEM_ID = 3;

    private MainWindow ui;

    /**
     * Ensures the chat is not GC-ed by the Java runtime. The {@link ChatManager} documentation points out:
     * <p/>
     * <em>
     * The chat manager keeps track of referneces to all current chats. It will not hold any references in memory on its
     * own so it is necessary to keep a reference to the chat object itself.
     * </em>
     * <p/>
     * If the chat is GC-ed, the Smack runtime will hand the message to a new Chat which will it create for the purpose.
     * In an interactive application, we would listen for and show these new chats, but our needs are different so we
     * add this quirk to stop it from happening.
     */
    private Chat notToBeGCed;

    public Main() throws Exception {
        startUserInterface();
    }

    public static void main(String... args) throws Exception {
        Main main = new Main();
        main.joinAuction(
                connection(args[ARG_HOSTNAME], args[ARG_USERNAME], args[ARG_PASSWORD]),
                args[ARG_ITEM_ID]
        );
    }

    private void startUserInterface() throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                ui = new MainWindow();
            }
        });
    }

    private void joinAuction(XMPPConnection connection, String itemId) throws XMPPException {
        final Chat chat = connection.getChatManager().createChat(
                auctionId(itemId, connection),
                new MessageListener() {
                    @Override
                    public void processMessage(Chat chat, Message message) {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                ui.showStatus(SniperStatus.LOST);
                            }
                        });
                    }
                }
        );
        this.notToBeGCed = chat;
        chat.sendMessage(new Message());
    }

    private static XMPPConnection connection(String hostname, String username, String password) throws XMPPException {
        XMPPConnection connection = new XMPPConnection(hostname);
        connection.connect();
        connection.login(username, password, AUCTION_RESOURCE);
        return connection;
    }

    private static String auctionId(String itemId, XMPPConnection connection) {
        return String.format(AUCTION_ID_FORMAT, itemId, connection.getServiceName());
    }
}
