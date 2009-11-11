package auctionsniper.ui;

import auctionsniper.ui.SniperStatus;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * The UI main frame (pun intended).
 *
 * @author Rob van der Linden Vooren
 */
public class MainWindow extends JFrame {

    public static final String MAIN_WINDOW_NAME = "Auction Sniper Main";
    public static final String SNIPER_STATUS_LABEL_NAME = "sniper status";

    private final JLabel sniperStatus = createLabel(SniperStatus.JOINING);

    public MainWindow() {
        super("Auction Sniper");
        setName(MAIN_WINDOW_NAME);

        add(sniperStatus);
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void showStatus(String status) {
        sniperStatus.setText(status);
    }

    private JLabel createLabel(String initialText) {
        JLabel label = new JLabel(initialText);
        label.setName(SNIPER_STATUS_LABEL_NAME);
        label.setBorder(new LineBorder(Color.BLACK));
        return label;
    }
}
