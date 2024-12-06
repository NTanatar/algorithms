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
        List<String> downLines = new ArrayList<>();

        for (int i = 0; i < data.getFirst().length(); i++) {
            StringBuilder downLine = new StringBuilder();
            for (String line : data) {
                downLine.append(line.charAt(i));
            }
            downLines.add(downLine.toString());
        }
        return addReversed(downLines);
    }

    public static List<String> getDiagonalLines(List<String> data) {
        Map<Integer, StringBuilder> diagonalLines = new HashMap<>();

        for (int i = 0; i < data.getFirst().length(); i++) {
            for (int j = 0; j < data.size(); j++) {
                append(diagonalLines, i + j, data.get(j).charAt(i));
            }
        }
        List<String> result = diagonalLines.values().stream()
            .map(StringBuilder::toString)
            .collect(toList());

        return addReversed(result);
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

    private static void append(Map<Integer, StringBuilder> diagonalLines, int key, char value) {
        if (diagonalLines.containsKey(key)) {
            diagonalLines.get(key).append(value);
        } else {
            diagonalLines.put(key, new StringBuilder(String.valueOf(value)));
        }
    }

    public static void main(String[] args) {
        String path = "C:\\learning\\git\\algorithms\\src\\main\\resources\\smallwords.txt";

        System.out.println("horizontal: " + getHorizontalLines(getFileContent(path)));
        System.out.println("vertical: " + getVerticalLines(getFileContent(path)));

        System.out.println("diagonal: " + getDiagonalLines(getFileContent("C:\\learning\\git\\algorithms\\src\\main\\resources\\diagonals.txt")));
    }
}
