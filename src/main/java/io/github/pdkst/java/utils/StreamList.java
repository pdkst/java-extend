package io.github.pdkst.java.utils;

import lombok.experimental.Delegate;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author pdkst
 */
public class StreamList<T> implements List<T> {
    private List<T> element;
    private Stream<T> stream;

    public StreamList() {
        element = new ArrayList<>();
    }

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

    public Set<T> toSet() {
        return toStream().collect(Collectors.toSet());
    }

    public <K> StreamMap<K, T> toMap(Function<? super T, ? extends K> keyMapper) {
        return this.toMap(keyMapper, Function.identity());
    }

    public <K, U> StreamMap<K, U> toMap(Function<? super T, ? extends K> keyMapper,
                                        Function<? super T, ? extends U> valueMapper) {
        return new StreamMap<K, U>(toStream().collect(Collectors.toMap(keyMapper, valueMapper)));
    }

    public <K, U> StreamMap<K, U> toConcurrentMap(Function<? super T, ? extends K> keyMapper,
                                                  Function<? super T, ? extends U> valueMapper) {
        return new StreamMap<K, U>(toStream().collect(Collectors.toConcurrentMap(keyMapper, valueMapper)));
    }

    public <KK> StreamMap<? extends KK, StreamList<T>> groupingBy(Function<? super T, ? extends KK> keyMapper) {
        return toStream().collect(Collectors.groupingBy(keyMapper, StreamMap::new, Collectors.toCollection(StreamList::new)));
    }

    public <KK, R> StreamMap<? extends KK, R> groupingBy(Function<? super T, ? extends KK> keyMapper, Collector<T, ?, R> downstream) {
        return toStream().collect(Collectors.groupingBy(keyMapper, StreamMap::new, downstream));
    }

    /**
     * T extends CharSequence
     *
     * @return joining
     */
    public String joining() {
        return toStream().map(t -> (CharSequence) t).collect(Collectors.joining());
    }

    /**
     * T extends CharSequence
     *
     * @return joining
     */
    public String joining(CharSequence delimiter) {
        return toStream().map(t -> (CharSequence) t).collect(Collectors.joining(delimiter));
    }

    /**
     * T extends CharSequence
     *
     * @return joining
     */
    public String joining(CharSequence delimiter,
                          CharSequence prefix,
                          CharSequence suffix) {
        return toStream().map(t -> (CharSequence) t).collect(Collectors.joining(delimiter, prefix, suffix));
    }

    public <R, A> R collect(Collector<? super T, A, R> collector) {
        return toStream().collect(collector);
    }

    public Stream<T> toStream() {
        if (stream != null) {
            return stream;
        } else {
            return element.stream();
        }
    }

    @Delegate
    public List<T> eval() {
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
