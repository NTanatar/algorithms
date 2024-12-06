package com.nata.sorting;

import java.util.List;

public class InsertionSort <T extends Comparable<T>> {

    public List<T> sort(List<T> list) {
        return sortPart(list);
    }

    private List<T> sortPart(List<T> list) {
        if (list.size() <= 1) {
            return list;
        }
        T last = list.removeLast();
        insertToSorted(sortPart(list), last);
        return list;
    }

    private void insertToSorted(List<T> sorted, T value) {
        int i = sorted.size() - 1;
        while (i >= 0 && value.compareTo(sorted.get(i)) < 0) {
            i--;
        }
        sorted.add(i + 1, value);
    }
}
