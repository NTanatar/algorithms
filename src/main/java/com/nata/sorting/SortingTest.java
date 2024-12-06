package com.nata.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class SortingTest {

    private static final List<Integer> LIST1 = new ArrayList<>(Arrays.asList(2,8,0,-4,17,23,45,7,9,11,5,3,1));
    private static final List<Integer> LIST2 = new ArrayList<>(Arrays.asList(39,10,6,5,10,15,4,9,10,6,7,9,2,49,-3));
    private static final List<Integer> LIST3 = new ArrayList<>(Arrays.asList(44,55,33,22,11, -22, -44));

    public static void main(String[] args) {

        System.out.println(new MergeSort<Integer>().sort(LIST1));
        System.out.println(new MergeSort<Integer>().sort(LIST2));
        System.out.println(new MergeSort<Integer>().sort(LIST3));
        System.out.println(new MergeSort<String>().sort(Set.of("mono", "abc", "brown", "mix", "black", "white")));

        System.out.println(new InsertionSort<Integer>().sort(LIST1));
        System.out.println(new InsertionSort<Integer>().sort(LIST2));
        System.out.println(new InsertionSort<Integer>().sort(LIST3));
    }
}
