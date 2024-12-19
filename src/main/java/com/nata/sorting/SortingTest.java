package com.nata.sorting;

import static com.nata.sorting.TestUtils.getRandomList;
import static com.nata.sorting.TestUtils.getReverseSortedList;
import static com.nata.sorting.TestUtils.getSortedList;
import static com.nata.sorting.TestUtils.testWithCollection;
import static com.nata.sorting.TestUtils.testWithList;

import java.util.List;
import java.util.Set;

public class SortingTest {

    public static void main(String[] args) {

        System.out.println(new MergeSort<String>().sort(Set.of("mono", "abc", "brown", "mix", "black", "white")));

        int listSize = 2000;
        MergeSort<Integer> mergeSort = new MergeSort<>();
        InsertionSort<Integer> insertionSort = new InsertionSort<>();
        QuickSort<Integer> quickSort = new QuickSort<>();

        System.out.println("-----------  with random input:");
        List<Integer> randomList = getRandomList(listSize);
        testWithCollection(randomList, mergeSort::sort);
        testWithList(randomList, insertionSort::sort);
        testWithList(randomList, quickSort::sort);

        System.out.println("-----------  with already sorted input:");
        List<Integer> list1 = getSortedList(listSize);
        testWithCollection(list1, mergeSort::sort);
        testWithList(list1, insertionSort::sort);
        testWithList(list1, quickSort::sort);

        System.out.println("-----------  with reverse sorted input:");
        List<Integer> list2 = getReverseSortedList(listSize);
        testWithCollection(list2, mergeSort::sort);
        testWithList(list2, insertionSort::sort);
        testWithList(list2, quickSort::sort);
    }
}
