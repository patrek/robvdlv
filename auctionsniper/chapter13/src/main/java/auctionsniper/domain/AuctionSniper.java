package auctionsniper.domain;

/**
 * Auction Sniper. Listens to events from an Auction and responds as it 'sees fit'.
 *
 * @author Rob van der Linden Vooren
 */
public class AuctionSniper implements AuctionEventListener {

    private final SniperListener sniperListener;
    private final Auction auction;

    public AuctionSniper(Auction auction, SniperListener sniperListener) {
        this.auction = auction;
        this.sniperListener = sniperListener;
    }

    @Override
    public void currentPrice(int price, int increment) {
        auction.bid(price + increment);
        sniperListener.sniperBidding();
    }

    @Override
    public void auctionClosed() {
        sniperListener.sniperLost();
    }
}
