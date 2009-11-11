package auctionsniper.functional;

import auctionsniper.ui.MainWindow;
import auctionsniper.util.Delay;
import static auctionsniper.util.Delay.millisFor;
import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JLabelDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;
import static org.hamcrest.Matchers.equalTo;

/**
 * WindowLicker JFrameDriver customized to our testing purposes.
 *
 * @author Rob van der Linden Vooren
 */
public class AuctionSniperDriver extends JFrameDriver {

    /**
     * Construct an AuctionSniperDriver which will timeout after {@code timeoutMillis} when a component is not found.
     *
     * @param timeoutMillis timeout in milliseconds after which a timeout will occur
     */
    public AuctionSniperDriver(long timeoutMillis) {
        super(new GesturePerformer(),
                JFrameDriver.topLevelFrame(
                        named(MainWindow.MAIN_WINDOW_NAME),
                        showingOnScreen()
                ),
                new AWTEventQueueProber(timeoutMillis, millisFor(Delay.TENTH_OF_A_SECOND))
        );
    }

    public void showsSniperStatus(String status) {
        new JLabelDriver(
                this,
                named(MainWindow.SNIPER_STATUS_LABEL_NAME)
        ).hasText(equalTo(status));
    }
}
