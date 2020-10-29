package io.github.pdkst.java.utils;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.experimental.Delegate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author pdkst
 */
public class StreamMap<K, V> implements Map<K, V> {
    public Map<K, V> value;
    public Stream<Map.Entry<K, V>> stream;

    public StreamMap() {
        value = new HashMap<>();
    }

    public StreamMap(Map<K, V> value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public StreamMap(Stream<Entry<K, V>> stream) {
        Objects.requireNonNull(stream);
        this.stream = stream;
    }

    public StreamMap<K, V> toConcurrentMap() {
        return new StreamMap<K, V>(new ConcurrentHashMap<>(eval()));
    }

    public <KK> StreamMap<KK, V> keyMap(Function<? super K, KK> keyMapper) {
        return new StreamMap<>(toStream().map(kvEntry -> new EntryImpl<>(keyMapper.apply(kvEntry.getKey()), kvEntry.getValue())));
    }

    public <VV> StreamMap<K, VV> valueMap(Function<? super V, VV> valueMapper) {
        return new StreamMap<>(toStream().map(kvEntry -> new EntryImpl<>(kvEntry.getKey(), valueMapper.apply(kvEntry.getValue()))));
    }

    public StreamList<K> keyList() {
        return new StreamList<>(eval().keySet().stream());
    }

    public StreamList<V> valueList() {
        return new StreamList<>(eval().values().stream());
    }

    public StreamMap<K, V> andPut(K key, V value) {
        this.put(key, value);
        return this;
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

    public static <K, V> StreamMap<K, V> init(K key, V value) {
        return StreamMap.<K, V>init().andPut(key, value);
    }

    public static <K, V> StreamMap<K, V> init() {
        return new StreamMap<>();
    }

    @AllArgsConstructor
    @EqualsAndHashCode
    private static class EntryImpl<K, V> implements Entry<K, V> {
        K k;
        V v;

        @Override
        public K getKey() {
            return k;
        }

        @Override
        public V getValue() {
            return v;
        }

        @Override
        public V setValue(V value) {
            V oldValue = v;
            v = value;
            return oldValue;
        }
    }
}
