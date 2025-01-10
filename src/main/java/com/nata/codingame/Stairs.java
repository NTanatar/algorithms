package com.nata.codingame;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Stairs {

    public static Set<List<Integer>> getStairs(int sum) {
        if (sum < 3) {
            return emptySet();
        }
        Set<List<Integer>> result = getAllPairs(sum);
        if (sum <= 5) {
            return result;
        }
        int last = sum - 3;
        while (last > 0) {
            Set<List<Integer>> combined = getCombinedStairs(sum - last, last);
            if (combined.isEmpty()) {
                break;
            }
            result.addAll(combined);
            last--;
        }
        return result;
    }

    private static Set<List<Integer>> getCombinedStairs(int sum, int last) {
        return getStairs(sum).stream()
            .filter(half -> half.getLast() < last)
            .map(half -> combine(half, last))
            .collect(toSet());
    }

    private static Set<List<Integer>> getAllPairs(int sum) {
        Set<List<Integer>> pairs = new HashSet<>();
        int half = sum % 2 == 0 ? sum / 2 - 1 : sum / 2;
        for (int i = 1; i <= half; i++) {
            pairs.add(List.of(i, sum - i));
        }
        return pairs;
    }

    private static List<Integer> combine(List<Integer> list, int last) {
        List<Integer> combined = new ArrayList<>(list);
        combined.add(last);
        return combined;
    }

    private static void print(Set<List<Integer>> set) {
        set.stream()
            .sorted(Stairs::compare)
            .forEach(System.out::println);
    }

    private static int compare(List<Integer> list1, List<Integer> list2) {
        if (list1.size() < list2.size()) {
            return -1;
        }
        if (list1.size() > list2.size()) {
            return 1;
        }
        for (int i = 0; i < list1.size(); i++) {
            if (list1.get(i) < list2.get(i)) {
                return -1;
            }
            if (list1.get(i) > list2.get(i)) {
                return 1;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        for(int i = 2; i <= 15; i++) {
            System.out.println("------------------");
            Set<List<Integer>> set = getStairs(i);
            System.out.println(i + ":  size= "+ set.size());
            print(set);
        }
    }
}
