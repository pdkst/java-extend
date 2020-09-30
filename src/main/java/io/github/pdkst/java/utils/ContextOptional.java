package io.github.pdkst.java.utils;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author pdkst
 */
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

    public <R> ContextOptional<R, C> map(Function<T, R> mapper) {
        final Optional<R> rOptional = Optional.ofNull(opt).map(mapper);
        return new ContextOptional<>(rOptional, context);
    }

    public <R> ContextOptional<R, C> mapWithContext(BiFunction<T, C, R> mapper) {
        Objects.requireNonNull(mapper);
        final Optional<R> rOptional = Optional.ofNull(opt).map(t -> mapper.apply(t, context));
        return new ContextOptional<>(rOptional, context);
    }
}
