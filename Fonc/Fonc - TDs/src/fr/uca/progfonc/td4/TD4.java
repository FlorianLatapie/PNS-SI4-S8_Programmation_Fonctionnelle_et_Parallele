package fr.uca.progfonc.td4;

import fr.uca.progfonc.td1.Lst;
import fr.uca.progfonc.td3.TD3;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TD4 {
    // Returns the maximum of a stream of comparable.
    public static <T extends Comparable<T>> Optional<T> max(BasicStream<T> s) {
        return s.reduce((x, y) -> x.compareTo(y) > 0 ? x : y);
    }

    // Returns a String composed of a representation of the stream elements joined together with specified delimiter.
    public static <T> String join(BasicStream<T> s, String delimiter) {
        return s.map(Object::toString)
                .reduce((a, b) -> a + delimiter + b)
                .orElse("");
    }

    // Returns a list that contains the n first elements of a stream.
    public static <T> List<T> collect(BasicStream<T> s, int n) {
        var res = new ArrayList<T>();
        s.limit(n).forEach(res::add);
        return res;
    }

    // Returns the average of all the even numbers in a given stream of integers. 0 if none.
    public static double averageEven(BasicStream<Integer> s) {
        var len = new AtomicInteger(1);
        return s
                .filter(x -> x % 2 == 0)
                .reduce((acc, current) -> {
                    len.incrementAndGet();
                    return acc + current;
                })
                .map(x -> x / len.get())
                .orElse(0);

    }

    // Returns a stream based on a list.
    public static <T> BasicStream<T> toStream(List<T> l) {
        var iterator = l.iterator();
        return new SupplierStream<>(
                () -> iterator.hasNext()
                        ? Optional.of(iterator.next())
                        : Optional.empty()
        );
    }

    // Returns a stream based on a Lst list.
    public static <T> BasicStream<T> toStream(Lst<T> l) {
        var reference = new AtomicReference<>(l);
        return new SupplierStream<>(
                () -> reference.get() == null
                        ? Optional.empty()
                        : Optional.of(
                        reference.getAndSet(reference.get().cdr()).car()
                )
        );
    }

    // Returns a stream of successive integers from 0 to "infinity"
    public static BasicStream<Long> longStream() {
        var value = new AtomicLong(0);
        return new SupplierStream<>(
                () -> Optional.of(value.getAndIncrement())
        );
    }

    // Returns the sum of n first numbers containing only digits 1 and 0 that are greater or equal than m
    public static long sum10(long m, int n) {
        return longStream()
                .filter(x -> x >= m)
                .filter(x -> Long.toString(x).matches("[10]+"))
                .limit(n)
                .reduce(Long::sum)
                .orElse(0L);
    }

    // Returns the square of all even numbers in a given list of integers.
    public static List<Integer> squareOfEven(List<Integer> l) {
        return l.stream().filter(x -> x % 2 == 0).map(x -> x * x).toList();
    }

    // Returns the average of all the numbers in a given list of integers.
    public static OptionalDouble average(List<Integer> l) {
        return l.stream().mapToInt(Integer::intValue).average();
    }

    // Returns the longest word in a given list of strings.
    public static Optional<String> longest(List<String> l) {
        return l.stream().max(Comparator.comparingInt(String::length));
    }

    // Returns the most common element in a given list of integers.
    public static <T> Optional<T> mostCommon(List<T> l) {
        return l.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    // Returns the first n Fibonacci numbers.
    public static List<Integer> fibs(int n) {
        return IntStream.range(0,n)
                .mapToObj(TD3::fib)
                .toList();
    }

    static IntPredicate isPrime = x -> true;

    // Returns an int stream of prime numbers based on eratosthenes sieve.
    public static IntStream eratosthenes() {
        return IntStream.iterate(2,x->x+1)
                .filter(x -> isPrime.test(x))
                .peek(x -> isPrime = isPrime.and(y -> y %x !=0));
    }
}



