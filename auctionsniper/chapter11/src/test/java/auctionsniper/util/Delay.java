package auctionsniper.util;

/**
 * Human readable delays.
 *
 * @author Rob van der Linden Vooren
 */
public enum Delay {

    TENTH_OF_A_SECOND(100);

    private final long millis;

    Delay(long millis) {
        this.millis = millis;
    }

    public static long millisFor(Delay delay) {
        return delay.millis;
    }
}
