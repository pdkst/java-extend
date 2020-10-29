package io.github.pdkst.java.utils;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;
import java.util.function.*;

/**
 * A optional with a context
 *
 * @author pdkst
 */
@ToString
@EqualsAndHashCode
public class ContextOptional<T, C> {
    private static final ContextOptional<Object, Object> NULL_CONTEXT = new ContextOptional<>((Object) null, null);
    private final C context;
    private final T value;

    private ContextOptional(T value, C context) {
        this.value = value;
        this.context = context;
    }

    private ContextOptional(Optional<T> opt, C context) {
        this.value = opt.orElseNull();
        this.context = context;
    }

    public ContextOptional<T, T> asContext() {
        if (isEmpty()) {
            return emptyContext();
        }
        return ofNull(value, value);
    }

    public <R> ContextOptional<R, C> map(Function<T, R> mapper) {
        Objects.requireNonNull(mapper);
        final Optional<R> rOptional = Optional.ofNull(value).map(mapper);
        return new ContextOptional<>(rOptional, context);
    }

    public ContextOptional<T, C> filter(Predicate<T> filter) {
        Objects.requireNonNull(filter);
        if (exists() && filter.test(value)) {
            return this;
        }
        return emptyContext();
    }

    public <R> ContextOptional<R, C> contextMap(BiFunction<? super C, ? super T, R> mapper) {
        Objects.requireNonNull(mapper);
        if (isEmpty()) {
            return emptyContext();
        }
        final R apply = mapper.apply(context, value);
        if (apply == null) {
            return emptyContext();
        }
        return ofNull(apply, context);
    }

    public ContextOptional<T, C> contextIfExists(BiConsumer<? super C, ? super T> mapper) {
        Objects.requireNonNull(mapper);
        if (exists()) {
            mapper.accept(context, value);
        }
        return this;
    }

    public ContextOptional<T, C> contextFilter(BiPredicate<? super C, ? super T> filter) {
        Objects.requireNonNull(filter);
        if (exists() && filter.test(context, value)) {
            return this;
        }
        return emptyContext();
    }

    public T orElse(T ifNull) {
        return exists() ? value : ifNull;
    }

    public boolean isEmpty() {
        return value == null;
    }

    public boolean exists() {
        return value != null;
    }

    @SuppressWarnings("unchecked")
    public static <T, C> ContextOptional<T, C> emptyContext() {
        return (ContextOptional<T, C>) NULL_CONTEXT;
    }

    public static <T, C> ContextOptional<T, C> ofNull(T t) {
        return ofNull(t, null);
    }

    public static <T, C> ContextOptional<T, C> ofNull(T t, C context) {
        return ofNull(Optional.ofNull(t), context);
    }

    public static <T, C> ContextOptional<T, C> ofNull(Optional<T> t, C context) {
        return new ContextOptional<>(Optional.ofNull(t), context);
    }
}
