package io.github.pdkst.java.utils;

import lombok.experimental.Delegate;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
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
        return new StreamList<T>(toStream().filter(predicate));
    }

    public <R> StreamList<R> map(Function<? super T, ? extends R> mapper) {
        return new StreamList<R>(toStream().map(mapper));
    }

    public <R> StreamList<R> flatMap(Function<? super T, ? extends Stream<R>> mapper) {
        return new StreamList<R>(toStream().flatMap(mapper));
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
}
