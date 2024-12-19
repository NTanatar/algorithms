package com.nata.sorting;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class TestUtils {

    public static List<Integer> getSortedList(int size) {
        List<Integer> result = new ArrayList<>(size);
        for (int i = 1; i <= size; i++) {
            result.add(i);
        }
        return result;
    }

    public static List<Integer> getReverseSortedList(int size) {
        List<Integer> result = new ArrayList<>(size);
        for (int i = size; i >= 1; i--) {
            result.add(i);
        }
        return result;
    }

    public static List<Integer> getRandomList(int size) {
        List<Integer> result = new ArrayList<>(size);
        Random random = new Random();
        for (int i = 1; i <= size; i++) {
            result.add(random.nextInt(size));
        }
        return result;
    }

    public static void testWithCollection(List<Integer> input, Function<Collection<Integer>, List<Integer>> sort) {
        Instant start = Instant.now();
        sort.apply(input);
        Instant finish = Instant.now();
        printTime(start, finish);
    }

    public static void testWithList(List<Integer> input, Function<List<Integer>, List<Integer>> sort) {
        Instant start = Instant.now();
        sort.apply(input);
        Instant finish = Instant.now();
        printTime(start, finish);
    }

    public static void printTime(Instant start, Instant finish) {
        long timeElapsed = Duration.between(start, finish).toNanos();
        System.out.println("Time: " + timeElapsed + " nanos");
    }
}
