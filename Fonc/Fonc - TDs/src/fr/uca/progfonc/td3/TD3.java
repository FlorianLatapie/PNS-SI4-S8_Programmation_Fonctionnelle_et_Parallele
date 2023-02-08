package fr.uca.progfonc.td3;

import fr.uca.progfonc.td1.Lst;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;

import static fr.uca.progfonc.td2.TD2.*;

public class TD3 {
    // Tail recursive factorial function
    public static int fact(int n) {
        return fact(n, 1);
    }

    private static int fact(int n, int acc) {
        if (n == 0) {
            return acc;
        }
        return fact(n - 1, acc * n);
    }

    // Tail recursive Fibonacci function
    public static int fib(int n) {
        return fib(n, 0, 1);
    }

    private static int fib(int n, int fib_n_1, int fib_n_2) {
        if (n == 0) {
            return fib_n_1;
        }
        if (n == 1) {
            return fib_n_2;
        }
        return fib(n - 1, fib_n_2, fib_n_1 + fib_n_2);
    }

    // Tail recursive function to calculate the greatest common divisor of two integers using Euclid algorithm
    public static int gcd(int a, int b) {
        if (a == 0) {
            return b;
        }
        if (b == 0) {
            return a;
        }
        if (a < b) {
            int tmp = b;
            b = a;
            a = tmp;
        }
        return gcd(b, a % b);
    }

    private static int gcd(int a, int b, int acc) {
        return -1;
    }

    public static String binary(int n) {
        return binary(n, "");
    }

    private static String binary(int n, String acc) {
        if (n == 0) {
            return 0 + acc;
        }
        if (n == 1) {
            return 1 + acc;
        }
        return binary(n / 2, n % 2 + acc);
    }

    // Tail recursive function to find minimum element in a list
    public static <T extends Comparable<T>> T min(Lst<T> lst) {
        if (lst == null) {
            return null;
        }
        return min(lst.cdr(), lst.car());
    }

    private static <T extends Comparable<T>> T min(Lst<T> lst, T currentMin) {
        if (lst == null) {
            return currentMin;
        }
        if (lst.car().compareTo(currentMin) < 0) {
            return min(lst.cdr(), lst.car());
        }
        return min(lst.cdr(), currentMin);
    }

    // Tail recursive function to reverse a linked list
    public static <T> Lst<T> reverse(Lst<T> current) {
        return reverse(current, null);
    }

    private static <T> Lst<T> reverse(Lst<T> current, Lst<T> previous) {
        if (current == null) {
            return previous;
        }
        return reverse(current.cdr(), new Lst<>(current.car(), previous));
    }

    // Tail recursive function to test if a list is a palindrome
    public static <T> boolean palindrome(Lst<T> l) {
        if (l == null) {
            return true;
        }
        return palindrome(l, reverse(l));
    }

    private static <T> boolean palindrome(Lst<T> lst, Lst<T> lrev) {
        if (lst == null) {
            return true;
        }
        if (!lst.car().equals(lrev.car())) {
            return false;
        }
        return palindrome(lst.cdr(), lrev.cdr());
    }

    public static <T> Lst<T> flatten(Lst<?> lst) {
        return reverse(flatten(lst, null));
    }

    public static <T> Lst<T> flatten(Lst<?> lst, Lst<T> flattened) {
        if (lst == null) {
            return flattened;
        }
        if (lst.car() instanceof Lst) {
            var tmp = flatten((Lst) lst.car(), flattened);
            return flatten(lst.cdr(), tmp);
        }
        return flatten(lst.cdr(), new Lst<>((T) lst.car(), flattened));
    }


    // Returns a new list that contains only the elements of the input list that are greater than n
    public static Lst<Integer> sup(Lst<Integer> l, int n) {
        return filter(x -> x.compareTo(n) > 0, l);
    }

    // Returns a list of n occurrences of e
    public static <T> Lst<T> nlist(int n, T e) {
        return nlist(n, e, null);
    }

    public static <T> Lst<T> nlist(int n, T e, Lst<T> acc) {
        if (n == 0) {
            return acc;
        }
        return nlist(n - 1, e, new Lst<>(e, acc));
    }


    // Produces the list of integers [0, 1, 2, ... n-1]
    public static Lst<Integer> indexes(int n) {
        var valeurQuOnMet = new AtomicInteger(0);
        return map(x -> valeurQuOnMet.getAndIncrement(), nlist(n, 0));
    }


    // Returns a closure that says if a character is a vowel.
    public static Predicate<Character> isVowel(String vowels) {
        return x -> vowels.indexOf(x) != -1;
    }

    public static Lst<Character> toLST(String s) {
        return toLST(s, 0, null);
    }

    private static Lst<Character> toLST(String s, int inexOfString, Lst<Character> acc) {
        if (inexOfString == s.length()) {
            return acc;
        }
        return toLST(s, inexOfString + 1, new Lst<>(s.charAt(inexOfString), acc));
    }


    // Returns a closure that counts the number of vowels in a string.
    public static Function<String, Integer> countVowels(String vowels) {
        return s -> count(isVowel(vowels), toLST(s));
    }

    // Returns a closure that computes if a string contains the substring.
    public static Predicate<String> contains(String sub) {
        return s -> s.contains(sub);
    }

    // Returns a closure that computes if the string s contains any of the elements of l.
    public static Predicate<String> containsAny(Lst<String> l) {
        return s -> reduce((x, y) -> s.contains(x) || y, l, false);
    }

    public static UnaryOperator<Integer> accumulator() {
        var x = new AtomicInteger(0);
        return x::addAndGet;
    }

    public static int sumAcc(Lst<Integer> list) {
        var acc = accumulator();
        return sumAcc(list, acc);
    }

    private static int sumAcc(Lst<Integer> list, UnaryOperator<Integer> acc) {
        if (list == null) {
            return acc.apply(0);
        }
        acc.apply(list.car());
        return sumAcc(list.cdr(), acc);
    }

    // Returns a non-safe closure for a list iterator.
    public static <T> Supplier<T> iterator(Lst<T> l) {
        return new Supplier<T>() {
            Lst<T> list = l;

            @Override
            public T get() {
                if (list == null) {
                    return null;
                }
                var tmp = list.car();
                list = list.cdr();
                return tmp;
            }
        };
    }

    // Returns the memoization of a function.
    public static <T, R> Function<T, R> memo(Function<T, R> f) {
        var history = new HashMap<T, R>();
        return key -> {
            if (history.containsKey(key)) {
                return history.get(key);
            }
            var res = f.apply(key);
            history.put(key, res);
            return res;
        };
    }

    public static <T, U, R> Function<T, Function<U, R>> curried(BiFunction<T, U, R> f) {
        return elem1 -> elem2 -> f.apply(elem1, elem2);
    }

    public static <T, U, R> BiFunction<T, U, R> unCurried(Function<T, Function<U, R>> f) {
        return (elem1, elem2) -> f.apply(elem1).apply(elem2);
    }
}
