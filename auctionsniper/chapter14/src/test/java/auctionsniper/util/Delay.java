package auctionsniper.util;

/**
 * Human readable delays.
 */
public enum Delay {

    TENTH_OF_A_SECOND(100),
    ONE_SECOND(1000);

    private final long millis;

    Delay(long millis) {
        this.millis = millis;
    }

    public static long millisFor(Delay delay) {
        return delay.millis;
    }
}
