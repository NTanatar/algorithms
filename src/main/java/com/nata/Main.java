package com.nata;

import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        List<Integer> result = new MergeSort<Integer>().merge(
            List.of(0,3,7,8,12,17,18,19),
            List.of(-1,4,5,9,11,15,24));

        System.out.println("merged 1: " + result);

        List<Integer> result1 = new MergeSort<Integer>().merge(
            List.of(5),
            List.of(3));

        System.out.println("merged 2:" + result1);

        List<Integer> result3 = new MergeSort<Integer>().sort(Set.of(
            6,13,2,8,0,-4,17,23,45,7,9,11,5,3,1));

        System.out.println("sorted 3: " + result3);

        List<Integer> result4 = new MergeSort<Integer>().sort(Set.of(
           0,3,4,6,7,9,2,5,10,15));

        System.out.println("sorted 4: " + result4);

        List<String> result5 = new MergeSort<String>().sort(Set.of(
            "mono", "abc", "brown", "mix", "black", "white"));

        System.out.println("sorted 5: " + result5);
    }
}