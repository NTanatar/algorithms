package com.nata.advent;

import static com.nata.advent.FileUtil.getFileContent;

import java.util.List;

public class FindXPattern {

    public static int countXPattern(String filePath, String pattern) {
        List<String> data = getFileContent(filePath);

        String reversedPattern = new StringBuilder(pattern).reverse().toString();

        int count = 0;
        for (int j = 1; j < data.size() - 1; j++) {
            for (int i = 1; i < data.getFirst().length() - 1; i++) {

                String d1 = String.valueOf(data.get(j - 1).charAt(i - 1))
                    + data.get(j).charAt(i)
                    + data.get(j + 1).charAt(i + 1);

                String d2 = String.valueOf(data.get(j - 1).charAt(i + 1))
                    + data.get(j).charAt(i)
                    + data.get(j + 1).charAt(i - 1);

                if ((d1.equals(pattern) || d1.equals(reversedPattern)) &&
                    (d2.equals(pattern) || d2.equals(reversedPattern))) {
                    count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(countXPattern("C:\\learning\\git\\algorithms\\src\\main\\resources\\smallwords.txt", "MAS"));
        System.out.println(countXPattern("C:\\learning\\git\\algorithms\\src\\main\\resources\\findaword.txt", "MAS"));
    }
}
