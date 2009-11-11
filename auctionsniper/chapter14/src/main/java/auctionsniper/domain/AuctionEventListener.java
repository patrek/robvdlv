package auctionsniper.domain;

/**
 * Listens to {@link Auction} events.
 */
public interface AuctionEventListener {

    enum PriceSource {
        FromSniper,
        FromOtherBidder
    }

    void currentPrice(int price, int increment, PriceSource source);

    void auctionClosed();

}
