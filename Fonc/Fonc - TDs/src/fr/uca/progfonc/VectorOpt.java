package fr.uca.progfonc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class VectorOpt{
    private List<Optional<?>> vec = new ArrayList<>();

    // a constructor with a size
    public VectorOpt(int size){
        for (int i = 0; i<size; i++){
            vec.add(Optional.empty());
        }
    }

    // get
    public Object get(int index) {
        if (vec.get(index).isEmpty())
            return Optional.empty();
        return vec.get(index);
    }

    // set
    // check if object is not already an Optional
    public void set(int index, Object value) {
        if (value instanceof Optional) {
            vec.set(index, (Optional<?>) value);
            return;
        }
        vec.set(index, Optional.of(value));
    }

    // stream (stream of the present values)
    public Stream<?> stream() {
        return vec.stream().filter(Optional::isPresent).map(Optional::get);
    }

    // peek (a selection of the vector entries)
    public VectorOpt peek(List<Integer> indexes) {
        VectorOpt sub = new VectorOpt(indexes.size());
        for (int i = 0; i < indexes.size(); i++) {
            sub.set(i, vec.get(indexes.get(i)));
        }
        return sub;
    }

    // and complete (true if all values are present)
    public boolean complete() {
        return vec.stream().allMatch(Optional::isPresent);
    }
}

/*
Write a static method allDifferent that returns a constraint that enforces that all values are different (empty values are not taken into account).

Par exemple:

Test	Résultat
VectorOpt v = new VectorOpt(5);
v.set(0, 10);
v.set(2, 30);
System.out.println(allDifferent().test(v))
true
VectorOpt v = new VectorOpt(5);
v.set(0, 10);
v.set(2, 30);
v.set(3, 10);
System.out.println(allDifferent().test(v))
false
 */

public static Constraint allDifferent() {
    return (VectorOpt v) -> {
        return v.stream().distinct().count() == v.stream().count();
    };



    public static Constraint exactSum(List<Integer> indexes, int goal) {
        return v -> {
            var peek = v.peek(indexes);
            return peek.stream().mapToInt(i -> (int) i).sum() == goal;
        };


        public static Constraint inSet(List<Object> indexes, Set<Object> goal){
            return v -> {== indexes.size();


                In our class
                Variables are represented by a List<String> (variable names)
                Domains are represented by a Map<String,List<?>> (variable names associated to possible values)
                Constraints are represented by a collection of functions: List<Constraint>
                Implement the two constraintFactory method that takes

                variables names and
                a predicate that takes as many arguments that the variables names given
                and produce the corresponding constraint applied on the correct variables. The predicate should return true if either variable is not present.

                        Par exemple:

                Test	Résultat
                CSP pb = new CSP();
                pb.addVariable("X", Stream.of(1, 2, 3));
                Constraint c = pb.constraintFactory("X",
                        x -> ((Integer) x).intValue() > 2);
                VectorOpt v = new VectorOpt(1);
                v.set(0, 3);
                System.out.println(c.test(v));
                v.set(0, 1);
                System.out.println(c.test(v));
                true
                false
                CSP pb = new CSP();
                pb.addVariable("X", Stream.of(1, 2, 3));
                pb.addVariable("Y", Stream.of(1, 2, 3));
                Constraint c = pb.constraintFactory("X", "Y",
                        (x, y) -> ((Integer) x).intValue() + ((Integer) y).intValue() > 2);
                VectorOpt v = new VectorOpt(2);
                v.set(0, 1);
                v.set(1, 1);
                System.out.println(c.test(v));
                v.set(1, 3);
                System.out.println(c.test(v));


                public Constraint constraintFactory(String v1, Predicate f) {
                    return (VectorOpt v) -> {
                        // peek
                        var peek = variables.get(v1).forEach(i -> v.set(i, v.get(i)));