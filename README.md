# java-extend
以下代码是等效的
### ArrayList
```java
//ReadmeTest.java
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
```

### StreamList
```java
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
```
输出：
```
789
123456
123456
```
