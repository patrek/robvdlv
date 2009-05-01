package nl.jteam.samples.domain;

/**
 * A Customer.
 */
public class Customer {

    private final String name;
    private final String emailAddress;

    public Customer(String name, String emailAddress) {
        this.name = name;
        this.emailAddress = emailAddress;
    }

    public String getName() {
        return name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}
