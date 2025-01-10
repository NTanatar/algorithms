package com.nata.codingame;

import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Stairs {

    public static Set<List<Integer>> getStairs(int sum) {
        if (sum < 3) {
            return emptySet();
        }
        if (sum == 3) {
            return singleton(List.of(1,2));
        }
        return increaseEach(getStairs(sum - 1));
    }

    private static Set<List<Integer>> increaseEach(Set<List<Integer>> set) {
        Set<List<Integer>> result = new HashSet<>();
        set.forEach(list -> result.addAll(increase(list)));
        return result;
    }

    private static Set<List<Integer>> increase(List<Integer> list) {
        Set<List<Integer>> result = new HashSet<>();
        if (list.getFirst() >= 2) {
            result.add(append(list));
        }
        for (int i = list.size() - 1; i >= 0; i--) {
            if (i == list.size() - 1 || list.get(i) + 1 < list.get(i + 1)) {
                List<Integer> candidate = new ArrayList<>(list);
                candidate.set(i, candidate.get(i) + 1);
                result.add(candidate);
            }
        }
        return result;
    }

    private static List<Integer> append(List<Integer> list) {
        List<Integer> result = new ArrayList<>();
        result.add(1);
        result.addAll(list);
        return result;
    }

    public static void main(String[] args) {
        for(int i = 2; i <= 10; i++) {
            System.out.println(i + ": " + getStairs(i));
        }
    }
}
