package auctionsniper.domain;

/**
 * Statuses of the {@link AuctionSniper}.
 */
public abstract class SniperStatus {

    public static final String LOST = "Lost";
    public static final String JOINING = "Joining";
    public static final String BIDDING = "Bidding";
    public static final String WINNING = "Winning";
    public static final String WON = "Won";

}
