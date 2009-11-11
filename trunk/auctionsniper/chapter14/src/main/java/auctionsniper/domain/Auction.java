package auctionsniper.domain;

/**
 * Auction. Is about financial transactions, accepts bids for items in the market.
 */
public interface Auction {

    void join();

    void bid(int amount);

}
