# java-extend

## StreamList
```java
@Test
public void testList() {
    final StreamList<Long> longs = new StreamList<String>(Arrays.asList("123", "456", "789", "123456"))
            .map(Long::new)
            .filter(aLong -> aLong > 500);
    longs.forEach(System.out::println);
    final StreamList<Long> filter = longs.filter(aLong -> aLong > 1000);
    filter.forEach(System.out::println);
}
```
