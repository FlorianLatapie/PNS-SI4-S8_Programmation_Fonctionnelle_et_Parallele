package fr.uca.progfonc.td1;

public class TD1 {
    /**
     * Returns the length of the list l.
     * @param l the list
     * @return the length of the list l
     */
    public static <T> int length(Lst<T> l) {
        if (l == null) {
            return 0;
        }
        return 1 + length(l.cdr());
    }

    /**
     * Checks if the value val is a member of the list l.
     * @param val the value
     * @param l the list
     * @return true if the value val is a member of the list l, false otherwise
     */
    public static <T> boolean member(T val, Lst<T> l) {
        if (l == null) {
            return false;
        }
        if (l.car().equals(val)) {
            return true;
        }

        return member(val, l.cdr());

        /* correction
        if (l==null){
            // Base case
            return false;
        } else {
            // Recursion
            return ((l.car().equals(val) || member(val, l.cdr())));
         */
    }

    /**
     * Adds the elements of list l1 to the beginning of list l2.
     * @param l1 the list to add
     * @param l2 the list to add to
     * @return the list containing the elements of l1 followed by the elements of l2
     */
    public static <T> Lst<T> append(Lst<T> l1, Lst<T> l2) {
        if (l1 == null) {
            return l2;
        }
        return new Lst<>(l1.car(), append(l1.cdr(), l2));
        // correction : same
    }

    /**
     * Calculates the sum of the elements in the list of integers l.
     * @param l the list of integers
     * @return the sum of the elements in the list of integers l
     */
    public static int sum(Lst<Integer> l) {
        if (l == null) {
            return 0;
        }
        return l.car() + sum(l.cdr());
    }

    /**
     * Removes the first occurrence of the value val from the list l.
     * @param val the value to remove
     * @param l the list
     * @return the list without the first occurrence of the value val
     */
    public static <T> Lst<T> remove(T val, Lst<T> l) {
        if (l == null) {
            return null;
        }
        if (val.equals(l.car())) {
            return l.cdr();
        }
        return new Lst<>(l.car(), remove(val, l.cdr()));
    }

    /**
     * Removes all occurrences of the value val from the list l.
     * @param val the value to remove
     * @param l the list
     * @return the list without the occurrences of the value val
     */
    public static <T> Lst<T> removeAll(T val, Lst<T> l) {
        if (l == null) {
            return null;
        }
        if (val.equals(l.car())) {
            return removeAll(val, l.cdr());
        }
        return new Lst<>(l.car(), removeAll(val, l.cdr()));
    }

    /**
     * Returns a list of strings containing the numbers from a to b, replacing multiples of 3 with "Fizz", multiples of 5 with "Buzz", and multiples of 15 with "FizzBuzz".
     * @param a the first number
     * @param b the last number
     * @return a list of strings containing fizzbuzz
     */
    public static Lst<String> fizzbuzz(int a, int b) {
        if (a >= b) {
            return null;
        }

        String val = "";
        if (a % 3 == 0) {
            val += "Fizz";
        }
        if (a % 5 == 0) {
            val += "Buzz";
        }
        if (val.isEmpty()) {
            val = a + "";
        }

        return new Lst<>(val, fizzbuzz(a + 1, b));
    }

    /**
     * Creates a list from a given array of elements of type T.
     * @param arr the array
     * @return the list
     */
    public static <T> Lst<T> fromArray(T[] arr) {
        return fromArray(arr, arr.length);
    }

    private static <T> Lst<T> fromArray(T[] arr, int i) {
        if (i == 0) {
            return null;
        }
        return new Lst<>(arr[arr.length - i], fromArray(arr, i - 1));
    }

    /**
     * Reverses the order of the elements in the list l.
     * @param l the list
     * @return the reversed list
     */
    public static <T> Lst<T> reverse(Lst<T> l) {
        return reverse(l, null);
    }

    private static <T> Lst<T> reverse(Lst<T> l, Lst<T> l2) {
        if (l == null) {
            return l2;
        }
        return reverse(l.cdr(), new Lst<>(l.car(), l2));
    }

    /**
     * Inserts the value val into the list l in a way that maintains the order of the elements in the list.
     * @param val the value to insert
     * @param l the list
     * @return the list with the value val inserted
     */
    public static <T extends Comparable<T>> Lst<T> insert(T val, Lst<T> l) {
        if (l == null) {
            return new Lst<>(val, null);
        }

        if (val.compareTo(l.car()) <= 0) {
            return new Lst<>(val, l);
        }

        return new Lst<>(l.car(), insert(val, l.cdr()));
    }

    /**
     * Sorts the list l using the insertion sort algorithm.
     * @param l the list
     * @return the sorted list
     */
    public static <T extends Comparable<T>> Lst<T> sort(Lst<T> l) {
        if (l == null) {
            return null;
        }
        return insert(l.car(), sort(l.cdr()));
    }

    /**
     * Returns a list containing the first n elements of the given list l. If n is less than or equal to 0 or l is null, it returns null.
     * @param n the number of elements to take
     * @param l the list
     * @return a list containing the first n elements of the given list l
     */
    public static <T> Lst<T> take(int n, Lst<T> l) {
        if (l == null || n<=0){
            return null;
        }
        return new Lst<>(l.car(), take(n-1, l.cdr()));
    }

    /**
     * Returns the index of the first occurrence of the value val in the list l. If the value is not in the list, it returns -1.
     * @param val the value to find
     * @param l the list
     * @return the index of the first occurrence of the value val in the list l
     */
    public static <T> int indexOf(T val, Lst<T> l) {
        return indexOf(val, l, 0);
    }

    private static <T> int indexOf(T val, Lst<T> l, int i) {
        if (l == null) {
            return -1;
        }
        if (val.equals(l.car())) {
            return i;
        }
        return indexOf(val, l.cdr(), i + 1);
    }

    /**
     * Returns a list containing only a unique occurrence of the elements in the list l.
     * @param l the list
     * @return a list containing only a unique occurrence of the elements in the list l
     */
    public static <T> Lst<T> unique(Lst<T> l) {
        if (l == null) {
            return null;
        }
        if (indexOf(l.car(), l.cdr()) != -1) {
            return unique(l.cdr());
        }
        return new Lst<>(l.car(), unique(l.cdr()));
    }

    /**
     * Returns true if the given key k is present in the list of pairs l, false otherwise.
     * @param l the list of pairs
     * @param k the key
     * @return
     */
    public static <T, U> boolean has(Lst<Pair<T, U>> l, T k) {
        if (l == null) {
            return false;
        }
        if (k.equals(l.car().key())) {
            return true;
        }
        return has(l.cdr(), k);
    }

    /**
     * Returns the value associated with the given key k in the list of pairs l, or null if k is not present in l.
     * @param l the list of pairs
     * @param k the key
     * @return the value associated with the given key k in the list of pairs l
     */
    public static <T, U> U get(Lst<Pair<T, U>> l, T k) {
        if (l == null) {
            return null;
        }
        if (k.equals(l.car().key())) {
            return l.car().value();
        }
        return get(l.cdr(), k);
    }

    /**
     * Change the value associated with the given key k to the given value v. If k is not present in l, it adds a new pair.
     * @param l the list of pairs
     * @param k the key
     * @param v the value
     * @return the list of pairs with the new value
     */
    public static <T, U> Lst<Pair<T, U>> set(Lst<Pair<T, U>> l, T k, U v) {
        if (l == null) {
            return new Lst<>(new Pair<>(k, v), null);
        }
        if (k.equals(l.car().key())) {
            return new Lst<>(new Pair<>(k, v), l.cdr());
        }
        return new Lst<>(l.car(), set(l.cdr(), k, v));
    }
}
