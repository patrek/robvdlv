package nl.jteam.samples.support;

import nl.jteam.samples.domain.Order;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import static java.lang.String.format;

public class SimpleOrderManager implements OrderManager {

    private MailSender mailSender;
    private SimpleMailMessage templateMessage;

    public void placeOrder(Order order) {
        // Do whatever is needed to process the order...
        sendCustomerNotificationFor(order);
    }

    private void sendCustomerNotificationFor(Order order) {
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo(order.getCustomer().getEmailAddress());
        msg.setText(format("%s thanks for order %s. We'll send the shizit ASAP.", order.getCustomer().getName(), order.getNumber()));
        try {
            this.mailSender.send(msg);
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setTemplateMessage(SimpleMailMessage templateMessage) {
        this.templateMessage = templateMessage;
    }
}
