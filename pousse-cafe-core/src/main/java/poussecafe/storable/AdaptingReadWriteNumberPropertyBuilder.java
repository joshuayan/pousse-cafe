package poussecafe.storable;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @param <U> Stored type
 * @param <T> Property type
 */
public class AdaptingReadWriteNumberPropertyBuilder<U, T> {

    AdaptingReadWriteNumberPropertyBuilder(Supplier<T> getter, Function<T, U> adapter) {
        this.getter = getter;
        this.adapter = adapter;
    }

    private Supplier<T> getter;

    private Function<T, U> adapter;

    public ReadWriteOptionalPropertyBuilder<T> set(Consumer<U> setter) {
        CompositeProperty<T, T> compositeProperty = new CompositeProperty<>();
        compositeProperty.getter = getter;
        compositeProperty.setter = value -> setter.accept(adapter.apply(value));
        return new ReadWriteOptionalPropertyBuilder<>(compositeProperty);
    }
}