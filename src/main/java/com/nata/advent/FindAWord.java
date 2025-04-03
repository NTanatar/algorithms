package com.nata.advent;

import static com.nata.advent.FileUtil.getFileContent;
import static com.nata.advent.Lines.getAllLines;

public class FindAWord {

    public static long countInFile(String word, String filePath) {
        return getAllLines(getFileContent(filePath)).stream()
            .map(line -> countInString(word, line))
            .reduce(0L, Long::sum);
    }

    public static long countInString(String word, String line) {
        long count = 0L;
        int i = line.indexOf(word);

        while (i >= 0) {
            count++;
            i = line.indexOf(word, i + 1);
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(countInString("xmas", "xmas"));
        System.out.println(countInString("xmas", "ss xmas fds xmas fr xmas ss"));

        System.out.println(countInString("abab", "ababab"));

        System.out.println(countInString("om", "skskskdkom"));
        System.out.println(countInString("om", "omkskskdkom"));
        System.out.println(countInString("om", "dfgromkskskdkom"));

        System.out.println(countInFile("XMAS", "smallwords.txt"));

        System.out.println(countInFile("XMAS", "findaword.txt"));
    }
}
