package com.nata.advent.lanparty;

import static com.nata.advent.FileUtil.getFileContent;

import java.util.List;

public class Main {

    public static LanParty initFromFile(String fileName) {
        List<String> lines = getFileContent(fileName);
        LanParty party = new LanParty();
        for (String line : lines) {
            String[] split = line.split("-");
            if (split.length == 2) {
                party.addConnection(split[0], split[1]);
            }
        }
        return party;
    }

    public static void main(String[] args) {
        LanParty small = initFromFile("C:\\learning\\git\\algorithms\\src\\main\\resources\\smalllanparty.txt");
        small.getTriples().stream()
            .sorted()
            .forEach(System.out::println);

        LanParty big = initFromFile("C:\\learning\\git\\algorithms\\src\\main\\resources\\biglanparty.txt");
        System.out.println("====> " + big.getTriples().size());
    }
}
