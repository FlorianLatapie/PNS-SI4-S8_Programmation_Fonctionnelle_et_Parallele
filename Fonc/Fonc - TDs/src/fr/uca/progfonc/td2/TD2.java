package fr.uca.progfonc.td2;

import fr.uca.progfonc.td1.Lst;
import fr.uca.progfonc.td1.TD1;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class TD2 {

    /**
     * map(f, l) takes a function f and a list l as parameters, and returns a new list containing the results of applying f to each element of l.
     */
    public static <T, R> Lst<R> map(Function<T, R> f, Lst<T> l) {
        if (l == null) {
            return null;
        }
        return new Lst<>(f.apply(l.car()), map(f, l.cdr()));
    }

    /**
     * Using map, write the following functions too :
     * squares(l) returns a new list containing the squares of each integer in the given list parameter.
     */
    public static Lst<Integer> squares(Lst<Integer> l) {
        return map(x -> x * x, l);
    }

    /**
     * Using map, write the following functions too :
     * sizeOfStrings(l) returns a new list containing the length of each string in the given list of strings parameter.
     */
    public static Lst<Integer> sizeOfStrings(Lst<String> l) {
        return map(String::length, l);
    }

    /**
     * filter(f, l) takes a predicate function f and a list l as parameters, and returns a new list containing only the elements of l for which f returns true.
     */
    public static <T> Lst<T> filter(Predicate<T> f, Lst<T> l) {
        if (l == null) {
            return null;
        }

        return f.test(l.car()) ? new Lst<>(l.car(), filter(f, l.cdr())) : filter(f, l.cdr());
    }

    /**
     * Using filter:
     * lowers(l) returns a new list containing only the strings in the given list parameter that are lowercase.
     */
    public static Lst<String> lowers(Lst<String> l) {
        return filter(s -> s.equals(s.toLowerCase()), l);
    }

    /**
     * count(f, l) takes a predicate function f and a list l as parameters, and returns the number of elements in l for which f returns true
     */
    public static <T> int count(Predicate<T> f, Lst<T> l) {
        if (l == null) {
            return 0;
        }
        return (f.test(l.car()) ? 1 : 0) + count(f, l.cdr());
    }

    /**
     * Using count:
     * nbPositives(l) returns the number of positive or null elements in the given integer list parameter.
     */
    public static int nbPositives(Lst<Integer> l) {
        return count(i -> i >= 0, l);
    }

    /**
     * reduce(f, l, init) takes a binary function f, a list l, and an initial value init as parameters, and recursively applies f to the elements of l using the previously calculated value to obtain a single value. For example, for the list (a b c), reduce must calculate f(a, f(b, f(c, init))). [FoldRight]
     */
    public static <T, R> R reduce(BiFunction<T, R, R> f, Lst<T> l, R init) {
        if (l == null) {
            return init;
        }
        return f.apply(l.car(), reduce(f, l.cdr(), init));
    }

    public static int sum(Lst<Integer> l) {
        return reduce(Integer::sum, l, 0);
    }

    public static <T extends Comparable<T>> T min(Lst<T> l) {
        if (l == null) {
            return null;
        }
        return reduce((x, y) -> x.compareTo(y) < 0 ? x : y, l, l.car());
    }

    public static int sumLengthLowers(Lst<String> l) {
        return reduce((x, y) -> x.equals(x.toLowerCase()) ? y + x.length() : y, l, 0);
    }

    public static int sumLength(Lst<String> l) {
        return 0;
    }

    public static <T> String repr(Lst<T> l) {
        return "(" + reduce((x, y) -> x + " " + y, l, "") + ")";
    }

    public static <T> Lst<T> concat(Lst<Lst<T>> ll) {
        return reduce(TD1::append, ll, null);
    }

    public static <T> Lst<T> toSet(Lst<T> l) {
        return reduce((x, y) -> TD1.member(x, y) ? y : new Lst<>(x, y), l, null);
    }

    public static <T> Lst<T> union(Lst<T> s1, Lst<T> s2) {
        return toSet(TD1.append(s1, s2));
    }

    public static <T> Predicate<T> equalsTo(T x) {
        return lmda -> lmda.equals(x);
    }

    public static <T extends Comparable<T>> Predicate<T> between(T a, T b) {
        return y -> y.compareTo(a) >= 0 && b.compareTo(y) >= 0;
    }

    public static <T> int countOccurence(Lst<T> l, T e) {
        return count(equalsTo(e), l);
    }
}
