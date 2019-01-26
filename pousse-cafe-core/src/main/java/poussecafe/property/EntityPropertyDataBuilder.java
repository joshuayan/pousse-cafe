package poussecafe.property;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import poussecafe.domain.Entity;
import poussecafe.domain.EntityData;

public class EntityPropertyDataBuilder<D extends EntityData<?>, E extends Entity<?, D>, F extends D> {

    EntityPropertyDataBuilder() {

    }

    Class<E> entityClass;

    Class<F> dataClass;

    public EntityPropertyDataBuilder<D, E, F> get(Supplier<F> getter) {
        Objects.requireNonNull(getter);
        this.getter = getter;
        return this;
    }

    private Supplier<F> getter;

    public EntityPropertyDataBuilder<D, E, F> set(Consumer<F> setter) {
        Objects.requireNonNull(setter);
        this.setter = setter;
        return this;
    }

    private Consumer<F> setter;

    public EntityProperty<E> build() {
        return new EntityPropertyData<D, E>(entityClass) {
            @Override
            protected D getData() {
                return getter.get();
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void setData(D data) {
                Objects.requireNonNull(data);
                setter.accept((F) data);
            }
        };
    }
}