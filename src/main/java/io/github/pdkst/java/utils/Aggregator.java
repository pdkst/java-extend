package io.github.pdkst.java.utils;

import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author pdkst
 * @since 2021/1/10
 */
public class Aggregator<T, U> {
    private final List<Aggregate<T, U>> aggregateList;
    private BiConsumer<T, U> finishConsumer;

    public Aggregator() {
        this.aggregateList = new ArrayList<>();
    }

    public Aggregator(List<Aggregate<T, U>> aggregateList) {
        this.aggregateList = aggregateList;
    }

    public void finish() {
        if (finishConsumer != null) {
            aggregateList.forEach(aggregate -> aggregate.finish(finishConsumer));
        }
    }

    public static <T, U> Aggregator<T, U> aggregate(List<T> target, List<U> extend) {
        final Aggregator<T, U> aggregator = new Aggregator<>();
        if (target == null || target.isEmpty()) {
            return aggregator;
        }
        final List<Aggregate<T, U>> aggregates = target.stream()
                .map(t -> Aggregate.of(t, (U) null))
                .collect(Collectors.toList());
        return new Aggregator<>(aggregates);
    }

    @Builder
    static class AggregatorCreator<T, U, R> {
        private List<T> target;
        private List<U> extend;
        private Function<T, R> targetFunction;
        private Function<U, R> extendFunction;


    }
}
