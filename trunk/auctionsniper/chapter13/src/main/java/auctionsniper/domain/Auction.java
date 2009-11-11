package auctionsniper.domain;

/**
 * Auction. Is about financial transactions, accepts bids for items in the market.
 *
 * @author Rob van der Linden Vooren
 */
public interface Auction {

    void join();

    void bid(int amount);

}
