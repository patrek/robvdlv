package auctionsniper.functional;

import auctionsniper.server.FakeAuctionServer;
import org.jivesoftware.smack.XMPPException;
import org.junit.After;
import org.junit.Test;

/**
 * "End-to-end" tests the AuctionSniper application, assuming the {@link auctionsniper.server.FakeAuctionServer} fakes
 * the real AuctionServer properly.
 *
 * @author Rob van der Linden Vooren
 */
public class AuctionSniperEndToEndTest {

    private final FakeAuctionServer auction = new FakeAuctionServer("item-54321");
    private final ApplicationRunner client = new ApplicationRunner();

    @After
    public void stopAuction() {
        auction.stop();
    }

    @After
    public void stopApplication() {
        client.stop();
    }

    @Test
    public void sniperJoinsAuctionUntilAuctionCloses() throws XMPPException, InterruptedException {
        // A note on naming:
        // In case of triggerering events to drive the test, style is "imperative" (startBiddingIn(..)).
        // In case of asserts, style is "indicative" (showsSniperHasLostAuction()).
        auction.startSellingItem();
        client.startBiddingIn(auction);
        auction.hasReceivedJoinRequestFromSniper();
        auction.announceClosed();
        client.showsSniperHasLostAuction();
    }
}
