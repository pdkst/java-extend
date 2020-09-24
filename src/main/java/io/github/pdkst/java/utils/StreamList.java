package io.github.pdkst.java.utils;

import lombok.experimental.Delegate;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author pdkst
 */
public class StreamList<T> implements List<T> {
    private List<T> element;
    private Stream<T> stream;

    public StreamList(List<T> element) {
        Objects.requireNonNull(element);
        this.element = element;
    }

    public StreamList(Stream<T> stream) {
        Objects.requireNonNull(stream);
        this.stream = stream;
    }

    public StreamList<T> filter(Predicate<? super T> predicate) {
        return new StreamList<>(toStream().filter(predicate));
    }

    public <R> StreamList<R> map(Function<? super T, ? extends R> mapper) {
        return new StreamList<>(toStream().map(mapper));
    }

    public <R> StreamList<R> flatMap(Function<? super T, ? extends Stream<R>> mapper) {
        return new StreamList<>(toStream().flatMap(mapper));
    }

    public StreamList<T> peek(Consumer<? super T> action) {
        return new StreamList<>(toStream().peek(action));
    }

    public StreamList<T> limit(long maxSize) {
        return new StreamList<>(toStream().limit(maxSize));
    }

    public StreamList<T> skip(long n) {
        return new StreamList<>(toStream().skip(n));
    }

    public StreamList<T> distinct() {
        return new StreamList<>(toStream().distinct());
    }

    public StreamList<T> sorted() {
        return new StreamList<T>(toStream().sorted());
    }

    public StreamList<T> sorted(Comparator<? super T> comparator) {
        return new StreamList<T>(toStream().sorted(comparator));
    }

    public StreamList<T> unordered() {
        return new StreamList<T>(toStream().unordered());
    }

    public StreamList<T> parallel() {
        return new StreamList<T>(toStream().parallel());
    }

    public void forStreamEach(Consumer<? super T> action) {
        toStream().forEach(action);
    }

    public void forStreamEachOrdered(Consumer<? super T> action) {
        toStream().forEachOrdered(action);
    }

    public T reduce(T identity, BinaryOperator<T> accumulator) {
        return toStream().reduce(identity, accumulator);
    }

    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        return toStream().reduce(accumulator);
    }

    public <U> U reduce(U identity,
                        BiFunction<U, ? super T, U> accumulator,
                        BinaryOperator<U> combiner) {
        return toStream().reduce(identity, accumulator, combiner);
    }

    public <K> StreamMap<K, T> toMap(Function<? super T, ? extends K> keyMapper) {
        return this.toMap(keyMapper, Function.identity());
    }

    public <K, U> StreamMap<K, U> toMap(Function<? super T, ? extends K> keyMapper,
                                        Function<? super T, ? extends U> valueMapper) {
        return new StreamMap<K, U>(toStream().collect(Collectors.toMap(keyMapper, valueMapper)));
    }

    Stream<T> toStream() {
        if (stream != null) {
            return stream;
        } else {
            return element.stream();
        }
    }

    @Delegate
    List<T> eval() {
        if (element == null) {
            element = this.stream.collect(Collectors.toList());
            stream = null;
        }
        return element;
    }

    public static <T> StreamList<T> empty() {
        return new StreamList<>(Stream.empty());
    }

    public static <T> StreamList<T> of(T t) {
        return new StreamList<>(Stream.of(t));
    }

    @SafeVarargs
    public static <T> StreamList<T> of(T... array) {
        return new StreamList<>(Stream.of(array));
    }
}
