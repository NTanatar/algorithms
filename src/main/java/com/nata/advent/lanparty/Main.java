package com.nata.advent.lanparty;

import static com.nata.advent.FileUtil.getFileContent;

import java.util.List;

public class Main {

    public static ComputerGroups initFromFile(String fileName) {
        List<String> lines = getFileContent(fileName);
        ComputerGroups groups = new ComputerGroups();
        for (String line : lines) {
            String[] split = line.split("-");
            if (split.length == 2) {
                groups.addConnection(split[0], split[1]);
            }
        }
        return groups;
    }

    public static void main(String[] args) {
        ComputerGroups computerGroups = initFromFile("C:\\learning\\git\\algorithms\\src\\main\\resources\\smalllanparty.txt");
        computerGroups.getTriples().stream()
            .filter(triple -> triple.contains("t"))
            .sorted()
            .forEach(System.out::println);
    }
}
