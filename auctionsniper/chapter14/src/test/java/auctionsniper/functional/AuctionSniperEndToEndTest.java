package auctionsniper.functional;

import auctionsniper.server.FakeAuctionServer;
import org.junit.After;
import org.junit.Test;

/**
 * "End-to-end" tests the AuctionSniper application, assuming the {@link FakeAuctionServer} fakes the production
 * AuctionServer properly.
 */
public class AuctionSniperEndToEndTest {

    private final FakeAuctionServer auction = new FakeAuctionServer("item-54321");
    private final ApplicationRunner application = new ApplicationRunner();

    @After
    public void stopAuction() {
        auction.stop();
    }

    @After
    public void stopApplication() {
        application.stop();
    }

    /*
        A note on naming:
        In case of triggerering events to drive the test, style is "imperative" (startBiddingIn(..)).
        In case of asserts, style is "indicative" (showsSniperHasLostAuction()).
    */

    @Test
    public void sniperJoinsAuctionUntilAuctionCloses() throws Exception {
        auction.startSellingItem();

        application.startBiddingIn(auction);
        // Wait for the fake auction to receive the Join request before continuing with the test.
        // This assertion is used to synchronize the Sniper with the auction.
        auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID);

        auction.announceClosed();

        application.showsSniperHasLostAuction();
    }

    @Test
    public void sniperMakesAHigherBidButLoses() throws Exception {
        auction.startSellingItem();

        application.startBiddingIn(auction);
        auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID);

        // Have the auction send a message back to the Sniper with the news that at the moment the price of the item is
        // 1000, the increment for the next bid is 98 and the winning bidder is "other bidder".
        auction.reportPrice(1000, 98, "other bidder");
        // Assert that the Sniper shows that it's now bidding after it's received the price updat emessage from the
        // auction.
        application.hasShownSniperIsBidding();

        // Assert that the auction has received a bid from Sniper that is equal to last price plus minimum increment.
        auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID);

        auction.announceClosed();
        application.showsSniperHasLostAuction();
    }

    @Test
    public void sniperWinsAnAuctionByBiddingHigher() throws Exception {
        auction.startSellingItem();

        application.startBiddingIn(auction);
        auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID);

        auction.reportPrice(1000, 98, "other bidder");
        application.hasShownSniperIsBidding();

        auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID);

        auction.reportPrice(1098, 97, ApplicationRunner.SNIPER_XMPP_ID);
        application.hasShownSniperisWinning();

        auction.announceClosed();
        application.showsSniperHasWonAuction();
    }
}
