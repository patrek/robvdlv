package auctionsniper.domain;

/**
 * Listens to {@link AuctionSniper} events. Is about feedback to the application, it reports changes to the current
 * state of the {@link AuctionSniper}.
 */
public interface SniperListener {

    void sniperBidding();

    void sniperLost();

    void sniperWinning();

    void sniperWon();

}
