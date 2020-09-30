package io.github.pdkst.java.utils;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author pdkst
 */
@ToString
@EqualsAndHashCode
public class ContextOptional<T, C> {
    private final Optional<T> opt;
    private final C context;

    public ContextOptional(Optional<T> opt, C context) {
        this.opt = opt;
        this.context = context;
    }

    public ContextOptional(T t, C context) {
        this(Optional.ofNull(t), context);
    }

    private  <R> ContextOptional<R, C> withContext(Optional<R> opt) {
        return new ContextOptional<>(opt, context);
    }

    public <R> ContextOptional<R, C> map(Function<T, R> mapper) {
        Objects.requireNonNull(mapper);
        final Optional<R> rOptional = Optional.ofNull(opt).map(mapper);
        return withContext(rOptional);
    }

    public <R> ContextOptional<R, C> mapWithContext(BiFunction<T, C, R> mapper) {
        Objects.requireNonNull(mapper);
        final Optional<R> rOptional = Optional.ofNull(opt).map(t -> mapper.apply(t, context));
        return new ContextOptional<>(rOptional, context);
    }

    public ContextOptional<T, C> ifExistsWithContext(BiConsumer<? super C, ? super T> mapper) {
        Objects.requireNonNull(mapper);
        Optional.ofNull(opt).ifExists(t -> mapper.accept(context, t));
        return this;
    }

    public T orElse(T ifNull) {
        return Optional.ofNull(opt).orElse(ifNull);
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
