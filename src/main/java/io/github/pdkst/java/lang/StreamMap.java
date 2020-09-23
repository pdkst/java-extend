package io.github.pdkst.java.lang;

import lombok.experimental.Delegate;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author pdkst
 */
public class StreamMap<K, V> implements Map<K, V> {
    public Map<K, V> value;
    public Stream<Map.Entry<K, V>> stream;

    public StreamMap(Map<K, V> value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public StreamMap(Stream<Entry<K, V>> stream) {
        Objects.requireNonNull(stream);
        this.stream = stream;
    }

    Stream<Map.Entry<K, V>> toStream() {
        if (stream != null) {
            return stream;
        } else {
            return value.entrySet().stream();
        }
    }

    @Delegate
    Map<K, V> eval() {
        if (value == null) {
            value = stream.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
            stream = null;
        }
        return value;
    }

}
