package com.nata;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MergeSort<T extends Comparable<T>> {

    public List<T> sort(Collection<T> collection) {
        if (collection == null || collection.isEmpty()) {
            return emptyList();
        }
        return sortList(new ArrayList<>(collection));
    }

    private List<T> sortList(List<T> list) {
        if (list.size() <= 1) {
            return list;
        }
        List<T> sorted1 = sortList(list.subList(0, list.size() / 2));
        List<T> sorted2 = sortList(list.subList(list.size() / 2, list.size()));
        return merge(sorted1, sorted2);
    }

    public List<T> merge(List<T> list1, List<T> list2) {
        List<T> result = new ArrayList<>(list1.size() + list2.size());
        int i1 = 0;
        int i2 = 0;
        while (i1 < list1.size() || i2 < list2.size()) {
            if (i2 == list2.size()) {
                result.add(list1.get(i1++));
            } else if (i1 == list1.size()) {
                result.add(list2.get(i2++));
            } else if (list1.get(i1).compareTo(list2.get(i2)) < 0) {
                result.add(list1.get(i1++));
            } else {
                result.add(list2.get(i2++));
            }
        }
        return result;
    }
}
