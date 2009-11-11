package auctionsniper.functional;

import auctionsniper.Main;
import auctionsniper.domain.SniperStatus;
import auctionsniper.server.FakeAuctionServer;
import static auctionsniper.server.FakeAuctionServer.XMPP_HOSTNAME;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Driver for the AuctionSniper user interface. Manages -and communicates with the Swing application.
 *
 * @author Rob van der Linden Vooren
 */
public class ApplicationRunner {

    public static final String SNIPER_ID = "sniper";
    public static final String SNIPER_PASSWORD = "sniper";
    public static final String SNIPER_XMPP_ID = SNIPER_ID + "@localhost/" + Main.AUCTION_RESOURCE;

    private AuctionSniperDriver driver;

    public void startBiddingIn(final FakeAuctionServer auction) {
        Thread thread = new Thread("Test Application") {
            // Application is called through main() to ensure proper internal object orchestration.
            // WindowLicker controls Swing components within the same JVM, therefor start a Sniper in its own thread.
            // Ideally, the test would start the Sniper in a new process but that would make testing much more
            // difficult. This seems a reasonable compromise.
            @Override
            public void run() {
                try {
                    Main.main(XMPP_HOSTNAME, SNIPER_ID, SNIPER_PASSWORD, auction.getItemId());
                } catch (Exception e) {
                    // Whatever test is run will fail, but stacktrace can be looked for in output.
                    // Later exceptions will be handled properly.
                    e.printStackTrace();
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
        driver = new AuctionSniperDriver(SECONDS.toMillis(1));
        driver.showsSniperStatus(SniperStatus.JOINING);
    }

    public void showsSniperHasLostAuction() {
        driver.showsSniperStatus(SniperStatus.LOST);
    }

    /**
     * Ensure window is disposed by the driver so that it won't be picked up in another test before garbage collection
     * kicking in.
     */
    public void stop() {
        if (driver == null) {
            return;
        }
        driver.dispose();
    }

    public void hasShownSniperIsBidding() {
        driver.showsSniperStatus(SniperStatus.BIDDING);
    }
}
