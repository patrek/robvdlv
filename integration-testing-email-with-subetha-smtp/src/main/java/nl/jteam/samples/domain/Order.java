package nl.jteam.samples.domain;

/**
 * An Order.
 */
public class Order {

    private final Customer customer;
    private final long number;

    public Order(long number, Customer customer) {
        this.number = number;
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public long getNumber() {
        return number;
    }
}
