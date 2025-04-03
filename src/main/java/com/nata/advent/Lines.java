package com.nata.advent;

import static com.nata.advent.FileUtil.getFileContent;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Lines {

    public static List<String> getAllLines(List<String> data) {
        return Stream.of(
                getHorizontalLines(data),
                getVerticalLines(data),
                getDiagonalLines(data),
                getDiagonalLines(getReversed(data)))
            .flatMap(Collection::stream)
            .collect(toList());
    }

    public static List<String> getHorizontalLines(List<String> data) {
        return addReversed(data);
    }

    public static List<String> getVerticalLines(List<String> data) {
        Map<Integer, StringBuilder> verticalLines = new HashMap<>();

        for (int i = 0; i < data.getFirst().length(); i++) {
            for (String s : data) {
                append(verticalLines, i, s.charAt(i));
            }
        }
        return addReversed(getValues(verticalLines));
    }

    public static List<String> getDiagonalLines(List<String> data) {
        Map<Integer, StringBuilder> diagonalLines = new HashMap<>();

        for (int i = 0; i < data.getFirst().length(); i++) {
            for (int j = 0; j < data.size(); j++) {
                append(diagonalLines, i + j, data.get(j).charAt(i));
            }
        }
        return addReversed(getValues(diagonalLines));
    }

    private static void append(Map<Integer, StringBuilder> map, int key, char value) {
        if (map.containsKey(key)) {
            map.get(key).append(value);
        } else {
            map.put(key, new StringBuilder(String.valueOf(value)));
        }
    }

    private static List<String> getValues(Map<Integer, StringBuilder> map) {
        return map.values().stream()
            .map(StringBuilder::toString)
            .collect(toList());
    }

    private static List<String> addReversed(List<String> data) {
        return Stream.of(data, getReversed(data))
            .flatMap(Collection::stream)
            .collect(toList());
    }

    private static List<String> getReversed(List<String> data) {
        return new ArrayList<>(data).stream()
            .map(s -> new StringBuilder(s).reverse().toString())
            .toList();
    }

    public static void main(String[] args) {
        String path = "smallwords.txt";

        System.out.println("horizontal: " + getHorizontalLines(getFileContent(path)));
        System.out.println("vertical: " + getVerticalLines(getFileContent(path)));

        System.out.println("diagonal: " + getDiagonalLines(getFileContent("diagonals.txt")));
    }
}
