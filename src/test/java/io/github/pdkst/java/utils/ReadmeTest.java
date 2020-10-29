package io.github.pdkst.java.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReadmeTest {

    @Test
    public void testArrayList() {
        //map => filter => toList()
        final List<String> sourceString = Arrays.asList("123", "456", "789", "123456");
        final List<Long> longs = new ArrayList<>(sourceString)
                .stream()
                .map(Long::new)
                .filter(aLong -> aLong > 500)
                .collect(Collectors.toList());
        longs.forEach(System.out::println);
        final List<Long> filter = longs.stream()
                .filter(aLong -> aLong > 1000)
                .collect(Collectors.toList());
        filter.forEach(System.out::println);
    }

    @Test
    public void testStreamList() {
        //map => filter => toList()
        final List<String> sourceString = Arrays.asList("123", "456", "789", "123456");
        final StreamList<Long> longs = new StreamList<>(sourceString)
                .map(Long::new)
                .filter(aLong -> aLong > 500);
        longs.forEach(System.out::println);
        final StreamList<Long> filter = longs.filter(aLong -> aLong > 1000);
        filter.forEach(System.out::println);
    }
}
