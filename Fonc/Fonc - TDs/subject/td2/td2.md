# TD2

Write the following functions :

- `map(f, l)` takes a function `f` and a list `l` as parameters, and returns a new list containing the results of applying `f` to each element of `l`.
  Using `map`, write the following functions too :
  - `squares(l)` returns a new list containing the squares of each integer in the given list parameter.
  - `sizeOfStrings(l)` returns a new list containing the length of each string in the given list of strings parameter.
- `filter(f, l)` takes a predicate function `f` and a list `l` as parameters, and returns a new list containing only the elements of `l` for which `f` returns `true`.
  Using `filter`:
  - `lowers(l)` returns a new list containing only the strings in the given list parameter that are lowercase.
- `count(f, l)` takes a predicate function `f` and a list `l` as parameters, and returns the number of elements in `l` for which `f` returns `true`.
  Using `count`:
  - `nbPositives(l)` returns the number of positive or null elements in the given integer list parameter.
- `reduce(f, l, init)` takes a binary function `f`, a list `l`, and an initial value `init` as parameters, and recursively applies `f` to the elements of `l` using the previously calculated value to obtain a single value. For example, for the list `(a b c)`, `reduce` must calculate `f(a, f(b, f(c, init)))`.  [FoldRight]
  Using `reduce`:
  - `sum(l)` returns the sum of the elements in the given integer list parameter.
  - `min(l)` returns the smallest element in the given list parameter.
  - `sumLengthLowers(l)` returns the sum of the length of each lowercase string in the given list of strings parameter.
  - `sumLength(l)` returns the sum of the length of each string in the given list of strings parameter.
  - `repr(l)` returns a string representation of the given list parameter. For example, `"(a b c )"`
  Using `reduce` and functions from TD1:
  - `concat(ll)` takes a list of lists `ll` as parameter and returns a new list containing the concatenation of all the lists in `ll`.
  - `toSet(l)` takes a list `l` as parameter and returns a new list containing only the unique elements of `l`.
  - `union(s1, s2)` takes two lists as parameters and returns the union of the two lists.
- `equalsTo(x)` takes an element `x` as parameter and returns a predicate function that checks if an element is equal to `x`.
  Using `equalsTo` and other functions:
  - `countOccurence(l, e)` takes a list `l` and an element `e` as parameters, and returns the number of occurrences of `e` in the list `l`.
- `between(a, b)` takes two elements `a` and `b` as parameters, and returns a predicate function that checks if an element is between `a` and `b` (inclusive).

> **Advice**
> use lambda functions or method references (`::`)