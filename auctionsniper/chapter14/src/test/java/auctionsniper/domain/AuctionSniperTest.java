package auctionsniper.domain;

import static auctionsniper.domain.AuctionEventListener.PriceSource;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.States;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests {@link AuctionSniper}.
 */
@RunWith(JMock.class)
public class AuctionSniperTest {

    private final Mockery context = new Mockery();
    private final SniperListener sniperListener = context.mock(SniperListener.class);
    private final Auction auction = context.mock(Auction.class);
    //We want to keep track of the Sniper's current state, as signlaed by the events it sends out, so we ask context for
    //a placeholder. The default state is null.
    private final States sniperState = context.states("sniper");
    private final AuctionSniper sniper = new AuctionSniper(auction, sniperListener);

    @Test
    public void reportsLostWhenAuctionClosesImmediately() {
        context.checking(new Expectations() {{
            atLeast(1).of(sniperListener).sniperLost();
        }});

        sniper.auctionClosed();
    }

    @Test
    public void reportsLostWhenAuctionClosesWhenBidding() {
        /*
            Allowances

            jMock distuingishes between *allowed* and *expected* invocations. An allowing() clause says that the object
            might make this call, but it doesn't have to--unlike an expectation which will fail the test if the call isn't
            made. This distinction helps to express what is important in a test (the underlying implementation is actually
            the same): expectations are what we want to confirm to have happened; allowances are supporting infrastructure
            that helps get the tested objects into the right state, or they're side effects we do not care about.
         */

        context.checking(new Expectations() {{
            //The Sniper will call auction but we really don't care about that *in this test*,
            //so we tell the test to ignore this collaborator completely.
            ignoring(auction);
            //When the Sniper sends out a bidding event, it's telling us that it's in a bidding state, recorded here.
            //We use the allowing() clause to communicate that this is a supporting part of the test, not the part we
            //really care about..
            allowing(sniperListener).sniperBidding();
            // This is the phrase that matters, the expectation we want to assert. If the Sniper isn't bidding when it
            // makes this call, the test will fail.
            then(sniperState.is("bidding"));

            atLeast(1).of(sniperListener).sniperLost();
            when(sniperState.is("bidding"));
        }});

        //This is our first test where we need a sequence of events to get the Sniper into the state we want to test.
        //We just call its methods in order.
        sniper.currentPrice(123, 45, PriceSource.FromOtherBidder);
        sniper.auctionClosed();

        /*
            Representing Object State

            In cases like this, we want to make assertions about an object's behavior depedending on its state, but we
            don't want to break encapsulation by exposing how that state is implemented. Instead, the test can listen
            to the notification events that the Sniper provides to tell interested collaborators about its state
            *in their terms*.jMock provides States objects, so that tests can record and make assertions about the state
            of an object when something significant happens, i.e. when it calls its neighbors.

            This is a 'logical' representation of what's going on inside the object, in this case the Sniper. It allows
            the test to describe what it finds relevant about the Sniper, regardless of how the Sniper is actually
            implemented.
         */
    }

    @Test
    public void bidsHigherAndReportsBiddingWhenNewPriceArrives() {
        final int price = 1001;
        final int increment = 25;
        context.checking(new Expectations() {{
            one(auction).bid(price + increment);
            atLeast(1).of(sniperListener).sniperBidding();
        }});

        sniper.currentPrice(price, increment, PriceSource.FromOtherBidder);
    }

    @Test
    public void reportsIsWinningWhenCurrentPriceComesFromSniper() {
        context.checking(new Expectations() {{
            atLeast(1).of(sniperListener).sniperWinning();
        }});

        sniper.currentPrice(123, 45, PriceSource.FromSniper);
    }

    @Test
    public void reportsWonIfAuctionClosesWhenWinning() {
        context.checking(new Expectations() {{
            ignoring(auction);
            allowing(sniperListener).sniperWinning();
            then(sniperState.is("winning"));

            atLeast(1).of(sniperListener).sniperWon();
            when(sniperState.is("winning"));
        }});

        sniper.currentPrice(123, 45, PriceSource.FromSniper);
        sniper.auctionClosed();
    }
}
