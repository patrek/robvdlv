package nl.ddd.eventbus;

public interface EventHandlerResolver<T> {

    EventHandler resolveEventHandler(T event);
}
