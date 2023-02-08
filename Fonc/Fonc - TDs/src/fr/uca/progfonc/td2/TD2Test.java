package fr.uca.progfonc.td2;

import fr.uca.progfonc.td1.Lst;
import fr.uca.progfonc.td1.TD1;
import org.junit.Test;

import static fr.uca.progfonc.td2.TD2.*;
import static org.junit.Assert.*;

public class TD2Test {
    @Test
    public void testMapEmptyList() {
        assertNull(map(Object::hashCode, null));
    }

    @Test
    public void testMapInitial() {
        Lst<String> list = new Lst<>("Hello", new Lst<>("world", null));
        Lst<Character> expectedResult = new Lst<>('H', new Lst<>('w', null));
        assertEquals(expectedResult, map(s -> s.charAt(0), list));
    }

    @Test
    public void testMapSign() {
        Lst<Integer> list = new Lst<>(12, new Lst<>(0, new Lst<>(-24, null)));
        Lst<Integer> expectedResult = new Lst<>(1, new Lst<>(0, new Lst<>(-1, null)));
        assertEquals(expectedResult, map(Integer::signum, list));
    }

    @Test
    public void testSquare() {
        Lst<Integer> list = new Lst<>(2, new Lst<>(0, new Lst<>(-4, null)));
        Lst<Integer> expectedResult = new Lst<>(4, new Lst<>(0, new Lst<>(16, null)));
        assertEquals(expectedResult, squares(list));
    }

    @Test
    public void testSizeOfStrings() {
        Lst<String> list = new Lst<>("Hello", new Lst<>("world!", null));
        Lst<Integer> expectedResult = new Lst<>(5, new Lst<>(6, null));
        assertEquals(expectedResult, sizeOfStrings(list));
    }

    @Test
    public void testFilterWithEmptyList() {
        Lst<Integer> emptyList = null;
        Lst<Integer> filteredList = filter(i -> i % 2 == 0, emptyList);
        assertEquals(null, filteredList);
    }

    @Test
    public void testFilterWithSingleElementList() {
        Lst<Integer> singleElementList = new Lst<>(1, null);
        Lst<Integer> filteredList = filter(i -> i % 2 == 0, singleElementList);
        assertEquals(null, filteredList);
    }

    @Test
    public void testFilterWithMultipleElements() {
        Lst<Integer> multipleElementsList = new Lst<>(1, new Lst<>(2, new Lst<>(3, new Lst<>(4, null))));
        Lst<Integer> filteredList = filter(i -> i % 2 == 0, multipleElementsList);
        assertEquals(new Lst<>(2, new Lst<>(4, null)), filteredList);
    }

    @Test
    public void testLowersWithMixedCaseElements() {
        Lst<String> multipleElementsList = new Lst<>("abc", new Lst<>("Def", new Lst<>("ghi", null)));
        Lst<String> lowersList = lowers(multipleElementsList);
        assertEquals(new Lst<>("abc", new Lst<>("ghi", null)), lowersList);
    }

    @Test
    public void testCountWithEmptyList() {
        Lst<Integer> emptyList = null;
        int count = count(i -> i % 2 == 0, emptyList);
        assertEquals(0, count);
    }

    @Test
    public void testCountWithSingleElementList() {
        Lst<Integer> singleElementList = new Lst<>(1, null);
        int count = count(i -> i % 2 == 0, singleElementList);
        assertEquals(0, count);
    }

    @Test
    public void testCountWithMultipleElements() {
        Lst<Integer> multipleElementsList = new Lst<>(1, new Lst<>(2, new Lst<>(3, new Lst<>(4, null))));
        int count = count(i -> i % 2 == 0, multipleElementsList);
        assertEquals(2, count);
    }

    @Test
    public void testNbPositives() {
        Lst<Integer> multipleElementsList = new Lst<>(-1, new Lst<>(0, new Lst<>(1, new Lst<>(2, null))));
        int count = nbPositives(multipleElementsList);
        assertEquals(3, count);
    }

    @Test
    public void testReduceWithEmptyList() {
        Lst<Integer> emptyList = null;
        int sum = reduce((i, j) -> i + j, emptyList, 0);
        assertEquals(0, sum);
    }

    @Test
    public void testReduceWithSingleElementList() {
        Lst<Integer> singleElementList = new Lst<>(1, null);
        int sum = reduce((i, j) -> i + j, singleElementList, 0);
        assertEquals(1, sum);
    }

    @Test
    public void testReduceWithMultipleElements() {
        Lst<Integer> multipleElementsList = new Lst<>(1, new Lst<>(2, new Lst<>(3, new Lst<>(4, null))));
        int sum = reduce((i, j) -> i + j, multipleElementsList, 0);
        assertEquals(10, sum);
    }

    @Test
    public void testReduceWithAllElements2() {
        Lst<Integer> multipleElementsList = new Lst<>(2, new Lst<>(4, new Lst<>(6, new Lst<>(8, null))));
        int product = reduce((i, j) -> i * j, multipleElementsList, 1);
        assertEquals(384, product);
    }

    @Test
    public void testReduceSizes() {
        Lst<String> multipleElementsList = new Lst<>("AbCD", new Lst<>("ef", new Lst<>("ghIjk", null)));
        int product = reduce((s, x) -> s.length() + x, multipleElementsList, 0);
        assertEquals(11, product);
    }

    @Test
    public void testSum() {
        Lst<Integer> multipleElementsList = new Lst<>(1, new Lst<>(2, new Lst<>(3, new Lst<>(4, null))));
        int sum = sum(multipleElementsList);
        assertEquals(10, sum);
    }

    @Test
    public void testMin() {
        Lst<Integer> multipleElementsList = new Lst<>(1, new Lst<>(-2, new Lst<>(-3, new Lst<>(4, null))));
        int result = min(multipleElementsList);
        assertEquals(-3, result);
    }

    @Test
    public void testMinFirst() {
        Lst<Integer> multipleElementsList = new Lst<>(1, new Lst<>(2, new Lst<>(3, new Lst<>(4, null))));
        int result = min(multipleElementsList);
        assertEquals(1, result);
    }

    @Test
    public void testSumLengthLowers() {
        Lst<String> multipleElementsList = new Lst<>("abc", new Lst<>("ADef", new Lst<>("ghi", null)));
        int result = sumLengthLowers(multipleElementsList);
        assertEquals(6, result);
    }

    @Test
    public void testReprInteger() {
        Lst<Integer> multipleElementsList = new Lst<>(1, new Lst<>(-2, new Lst<>(-3, new Lst<>(4, null))));
        String result = repr(multipleElementsList);
        assertEquals("(1 -2 -3 4 )", result);
    }

    @Test
    public void testReprString() {
        Lst<String> multipleElementsList = new Lst<>("abc", new Lst<>("Def", new Lst<>("ghi", null)));
        String result = repr(multipleElementsList);
        assertEquals("(abc Def ghi )", result);
    }

    @Test
    public void testConcat() {
        Lst<Lst<Integer>> multipleElementsList = new Lst<>(new Lst<>(1, new Lst<>(2, new Lst<>(3, null))),
                new Lst<>(new Lst<>(-1, new Lst<>(-2, new Lst<>(-3, null))),
                        new Lst<>(new Lst<>(0, null), null)));
        Lst<Integer> expected = new Lst<>(1, new Lst<>(2, new Lst<>(3, new Lst<>(-1, new Lst<>(-2, new Lst<>(-3, new Lst<>(0, null)))))));
        assertEquals(expected, concat(multipleElementsList));
    }

    @Test
    public void testToSet() {
        Lst<Integer> multipleElementsList = new Lst<>(1, new Lst<>(2, new Lst<>(1, new Lst<>(3, null))));
        Lst<Integer> expected = new Lst<>(1, new Lst<>(2, new Lst<>(3, null)));
        assertEquals(expected, TD1.sort(toSet(multipleElementsList)));
    }

    @Test
    public void testUnion() {
        Lst<Integer> multipleElementsList = new Lst<>(3, new Lst<>(2, new Lst<>(1, null)));
        Lst<Integer> otherList = new Lst<>(4, new Lst<>(2, new Lst<>(0, null)));
        Lst<Integer> expected = new Lst<>(0, new Lst<>(1, new Lst<>(2, new Lst<>(3, new Lst<>(4, null)))));
        assertEquals(expected, TD1.sort(union(multipleElementsList, otherList)));
    }

    @Test
    public void testEqualsTo() {
        assertTrue(equalsTo("ABC").test("ABC"));
        assertFalse(equalsTo("ABC").test("abc"));
    }

    @Test
    public void testBetween() {
        assertTrue(between(5, 17).test(12));
        assertTrue(between(5, 17).test(5));
        assertFalse(between(5, 17).test(30));
    }

    @Test
    public void testCountOccurence() {
        Lst<Integer> multipleElementsList = new Lst<>(1, new Lst<>(2, new Lst<>(1, new Lst<>(3, null))));
        assertEquals(2, countOccurence(multipleElementsList, 1));
        assertEquals(0, countOccurence(multipleElementsList, 4));
    }
}
