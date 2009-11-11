package auctionsniper;

/**
 * ?
 *
 * @author Rob van der Linden Vooren
 */
public interface AuctionEventListener {

    void auctionClosed();

    void currentPrice(int price, int increment);

}
