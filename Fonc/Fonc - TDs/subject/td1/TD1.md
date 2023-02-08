# TD1

Given the following record Lst:

```java
public record Lst<T>(T car, Lst<T> cdr) { }
```

Write the following functions:

- `length(l)` returns the length of the list `l`.
- `member(val, l)` checks if the value `val` is a member of the list `l`.
- `append(l1, l2)` adds the elements of list `l1` to the beginning of list `l2`.
- `sum(l)` calculates the sum of the elements in the list of integers `l`.
- `remove(val, l)` removes the first occurrence of the value `val` from the list `l`.
- `removeAll(val, l)` removes all occurrences of the value `val` from the list `l`.
- `fizzbuzz(a, b)` returns a list of strings containing the numbers from `a` to `b`, replacing multiples of 3 with "Fizz", multiples of 5 with "Buzz", and multiples of 15 with "FizzBuzz".
- `fromArray(tab)` creates a list from a given array of elements of type `T`.
- `reverse(l)` reverses the order of the elements in the list `l`.
- `insert(val, l)` inserts the value `val` into the list `l` in a way that maintains the order of the elements in the list.
- `sort(l)` sorts the list `l` using the insertion sort algorithm.
- `take(n, l)` returns a list containing the first `n` elements of the given list `l`. If `n` is less than or equal to 0 or `l` is null, it returns null.
- `indexOf(val, l)` returns the index of the first occurrence of the given value `val` in the list `l`, or -1 if `val` is not found in `l`.
- `unique(l)` returns a list containing only a unique occurence of the elements of the given list `l`.

Write a record `Pair` containing a pair `<key, value>` and write the following functions:

- `has(l, k)` returns true if the given key `k` is present in the list of pairs `l`, and false otherwise.
- `get(l, k)` returns the value associated with the given key `k` in the list of pairs `l`, or null if `k` is not present in `l`.
- `set(l, k, v)` change the value associated with the given key `k` to the given value `v`. If `k` is not present in `l`, it adds a new pair.

> **Tip**
> write these functions in a recursive way.
