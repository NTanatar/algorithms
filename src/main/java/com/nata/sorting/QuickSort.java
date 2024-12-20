package com.nata.sorting;

import static com.nata.sorting.TestUtils.getRandomList;
import static com.nata.sorting.TestUtils.getReverseSortedList;
import static com.nata.sorting.TestUtils.getSortedList;
import static com.nata.sorting.TestUtils.testWithList;
import static java.util.Collections.emptyList;
import static java.util.Collections.swap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import lombok.Setter;

@Setter
public class QuickSort<T extends Comparable<T>> {

    private Function<List<T>, Integer> pivotFunction = list -> list.size() - 1;

    public QuickSort<T> withLastElementAsPivot() {
        pivotFunction = list -> list.size() - 1;
        return this;
    }

    public QuickSort<T> withFirstElementAsPivot() {
        pivotFunction = _ -> 0;
        return this;
    }

    public QuickSort<T> withRandomElementAsPivot() {
        Random random = new Random();
        pivotFunction = list -> random.nextInt(0, list.size() - 1);
        return this;
    }

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
        int pivotIndex = pivotFunction.apply(items);
        T pivot = items.get(pivotIndex);
        swap(items, pivotIndex, j);

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

    public void sortAndPrint(List<T> input) {
        System.out.println("input:  " + input);
        System.out.println("result: " + sort(input));
    }

    public static List<QuickSort<Integer>> createInstances() {
        return List.of(
            new QuickSort<Integer>().withLastElementAsPivot(),
            new QuickSort<Integer>().withFirstElementAsPivot(),
            new QuickSort<Integer>().withRandomElementAsPivot());
    }

    public static void testAndPrintWithAllPivotFunctions(int inputSize) {
        List<QuickSort<Integer>> instances = createInstances();

        System.out.println("-----------  with random input:");
        List<Integer> randomList = getRandomList(inputSize);
        instances.forEach(s -> s.sortAndPrint(randomList));

        System.out.println("-----------  with already sorted input:");
        List<Integer> list1 = getSortedList(inputSize);
        instances.forEach(s -> s.sortAndPrint(list1));

        System.out.println("-----------  with reverse sorted input:");
        List<Integer> list2 = getReverseSortedList(inputSize);
        instances.forEach(s -> s.sortAndPrint(list2));
    }

    public static void performanceTestWithAllPivotFunctions(int inputSize) {
        List<QuickSort<Integer>> instances = createInstances();

        System.out.println("-----------  with random input:");
        List<Integer> randomList = getRandomList(inputSize);
        instances.forEach(s -> testWithList(randomList, s::sort));

        System.out.println("-----------  with already sorted input:");
        List<Integer> list1 = getSortedList(inputSize);
        instances.forEach(s -> testWithList(list1, s::sort));

        System.out.println("-----------  with reverse sorted input:");
        List<Integer> list2 = getReverseSortedList(inputSize);
        instances.forEach(s -> testWithList(list2, s::sort));
    }

    public static void main(String[] args) {
        testAndPrintWithAllPivotFunctions(30);
        performanceTestWithAllPivotFunctions(2500);
    }
}
