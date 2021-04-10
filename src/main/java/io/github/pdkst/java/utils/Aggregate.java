package io.github.pdkst.java.utils;

import java.util.function.BiConsumer;

/**
 * @author pdkst
 * @since 2021/1/10
 */
public class Aggregate<T, U> {
    private final T target;
    private U extend;

    public void setExtend(U extend) {
        this.extend = extend;
    }

    private Aggregate(T target, U extend) {
        this.target = target;
        this.extend = extend;
    }

    public void finish(BiConsumer<T, U> consumer) {
        if (extend != null) {
            consumer.accept(target, extend);
        }
    }

    public static <T, U> Aggregate<T, U> of(T target, U extend) {
        if (target == null) {
            throw new NullPointerException();
        }
        return new Aggregate<>(target, extend);
    }

    public static <T, U> Aggregate<T, U> of(T target) {
        return of(target, (U)null);
    }
}
