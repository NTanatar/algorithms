package com.nata.sorting;

import static java.util.Collections.emptyList;
import static java.util.Collections.swap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QuickSort<T extends Comparable<T>> {

    public List<T> sort(Collection<T> items) {
        if (items == null || items.isEmpty()) {
            return emptyList();
        }
        return sortPart(new ArrayList<>(items));
    }

    private List<T> sortPart(List<T> items) {
        if (items.size() <= 1) {
            return items;
        }
        if (items.size() == 2) {
            if (items.get(0).compareTo(items.get(1)) > 0) {
                swap(items, 0, 1);
            }
            return items;
        }
        int p = partition(items);
        sortPart(items.subList(0, p));
        sortPart(items.subList(p, items.size()));
        return items;
    }

    private int partition(List<T> items) {
        int i = 0;
        int j = items.size() - 1;
        T pivot = items.getLast();

        while (i < j) {
            while (i < j && items.get(i).compareTo(pivot) < 0) {
                i++;
            }
            while (j > i && items.get(j).compareTo(pivot) > 0) {
                j--;
            }
            if (i < j) {
                swap(items, i, j);
                i++;
                j--;
            }
        }
        if (items.get(i).compareTo(pivot) < 0) {
            return i + 1;
        } else {
            return i;
        }
    }

    public static void test(List<Integer> numbers) {
        System.out.println(new QuickSort<Integer>().sort(numbers));
    }

    public static void main(String[] args) {
        test(List.of(1,2,3));
        test(List.of(2,3,1));
        test(List.of(3,1,2));
        test(List.of(1,3,2));
        test(List.of(3,2,1));
        test(List.of(2,1,3));
        test(List.of(2,4,5,3));
        test(List.of(3,5,4,2));

        test(List.of(4,5,2,8,7,5,1,9,-3,-2,0,10,8));

        test(List.of(4,-6,2,18,7,15,1,9,-3,-2,0,10,8,12,3,6));
    }
}
