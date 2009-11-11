package auctionsniper.domain;

/**
 * Listens to {@link AuctionSniper} events. Is about feedback to the application, it reports changes to the current
 * state of the {@link AuctionSniper}.
 *
 * @author Rob van der Linden Vooren
 */
public interface SniperListener {

    void sniperBidding();

    void sniperLost();

}
