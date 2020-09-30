package io.github.pdkst.java.utils;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author pdkst
 * @see java.util.Optional
 */
public class Optional<T> {
    private final T o;
    private static final Optional<?> EMPTY = new Optional<>(null);

    protected Optional(T o) {
        this.o = o;
    }

    @SuppressWarnings("unchecked")
    public static <T> Optional<T> ofNull() {
        return (Optional<T>) EMPTY;
    }

    public static <T> Optional<T> ofNull(T t) {
        return t == null ? ofNull() : new Optional<>(t);
    }

    public static <T> Optional<T> ofNull(Optional<T> t) {
        return t == null ? ofNull() : t;
    }

    public static <T> Optional<T> ofNull(Supplier<T> t) {
        return t == null ? ofNull() : ofNull(t.get());
    }

    public <R> Optional<R> map(Function<T, R> mapper) {
        Objects.requireNonNull(mapper);
        if (isEmpty()) {
            return ofNull();
        }
        return ofNull(mapper.apply(o));
    }

    public <R> Optional<R> flatMap(Function<T, Optional<R>> mapper) {
        Objects.requireNonNull(mapper);
        if (isEmpty()) {
            return ofNull();
        }
        final Optional<R> apply = mapper.apply(o);
        return ofNull(apply);
    }

    public Optional<T> peek(Consumer<? super T> consumer) {
        Objects.requireNonNull(consumer);
        if (isEmpty()) {
            return this;
        }
        consumer.accept(o);
        return this;
    }

    public void ifExists(Consumer<? super T> consumer) {
        peek(consumer);
    }

    public Optional<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        return exists() && predicate.test(o) ? this : ofNull();
    }

    public T orElse(T ifNull) {
        return isEmpty() ? ifNull : o;
    }

    public T orElse(Optional<T> ifNullOpt) {
        return exists() ? o : (ifNullOpt == null ? null : ifNullOpt.orElse((T) null));
    }

    public Optional<T> otherwise(T ifNull) {
        return exists() ? this : ofNull(ifNull);
    }

    public Optional<T> otherwise(Optional<T> ifNullOpt) {
        return exists() ? this : ofNull(ifNullOpt);
    }

    public Optional<T> otherwise(Supplier<? extends T> ifNullSupply) {
        Objects.requireNonNull(ifNullSupply);
        return exists() ? this : ofNull(ifNullSupply.get());
    }

    public boolean exists() {
        return o != null;
    }

    public boolean isEmpty() {
        return o == null;
    }

    public ContextOptional<T, T> withThisContext() {
        return new ContextOptional<>(this, o);
    }
}
