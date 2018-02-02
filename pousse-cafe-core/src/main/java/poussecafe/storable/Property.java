package poussecafe.storable;

public interface Property<T> {

    T get();

    void set(T value);

    Class<? extends T> getValueClass();
}
