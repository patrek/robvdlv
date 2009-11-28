package nl.ddd.domain;

/**
 * @author Rob van der Linden Vooren
 */
public class Isbn {

    private final String name;

    public Isbn(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}