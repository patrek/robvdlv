package auctionsniper.domain;

/**
 * Listens to {@link Auction} events.
 *
 * @author Rob van der Linden Vooren
 */
public interface AuctionEventListener {

    void currentPrice(int price, int increment);

    void auctionClosed();

}
