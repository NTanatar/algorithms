package com.nata.advent.race;

import static com.nata.advent.FileUtil.getFileContent;
import static java.lang.Math.abs;
import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

import com.nata.advent.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CheatCalculator {

    private final RaceField field;

    public Map<Integer, Set<Cheat>> getBigCheats(int minWin) {
        if (minWin > field.getRaceTrack().size()) {
            return emptyMap();
        }
        List<Cheat> cheats = new ArrayList<>();
        int minPathDifference = minWin + 2; // min cost of a cheat
        for (int i = 0; i < field.getRaceTrack().size() - minPathDifference; i++) {
            for (int j = i + minPathDifference; j < field.getRaceTrack().size(); j++) {
                Position p1 = field.getRaceTrack().get(i);
                Position p2 = field.getRaceTrack().get(j);
                int pathDiff = j - i;
                int cheatCost = abs(p2.getX() - p1.getX()) + abs(p2.getY() - p1.getY());
                if (cheatCost <= 20 && pathDiff - cheatCost >= minWin) {
                    cheats.add(Cheat.builder()
                        .p1(p1)
                        .p2(p2)
                        .win(pathDiff - cheatCost)
                        .build());
                }
            }
        }
        return cheats.stream().collect(groupingBy(Cheat::getWin, toSet()));
    }

    public Map<Integer, Set<Cheat>> calculateSmallCheats() {
        List<Cheat> cheats = new ArrayList<>();

        for (int y = 1; y < field.getHeight() - 1; y++) {
            for (int x = 1; x < field.getWidth() - 1; x++) {
                cheats.addAll(getCheatsForWall(x, y));
            }
        }
        return cheats.stream().collect(groupingBy(Cheat::getWin, toSet()));
    }

    private List<Cheat> getCheatsForWall(int x, int y) {
        List<Cheat> cheats = new ArrayList<>();
        if (field.getMap(x,y) != '#') {
            return cheats;
        }
        if (canCheatHorizontally(x, y)) {
            cheats.add(createHorizontalCheat(x, y));
        }
        if (canCheatVertically(x, y)) {
            cheats.add(createVerticalCheat(x, y));
        }
        return cheats;
    }

    private boolean canCheatHorizontally(int x, int y) {
        return field.getPathValues()[x - 1][y] != null && field.getPathValues()[x + 1][y] != null;
    }

    private boolean canCheatVertically(int x, int y) {
        return field.getPathValues()[x][y - 1] != null && field.getPathValues()[x][y + 1] != null;
    }

    private Cheat createHorizontalCheat(int x, int y) {
        return createCheat(new Position(x, y), new Position(x + 1, y), new Position(x - 1, y));
    }

    private Cheat createVerticalCheat(int x, int y) {
        return createCheat(new Position(x, y), new Position(x, y - 1), new Position(x, y + 1));
    }

    private Cheat createCheat(Position wall, Position a, Position b) {
        Position p1 = field.getPathValue(a) < field.getPathValue(b) ? a : b;
        Position p2 = field.getPathValue(a) > field.getPathValue(b) ? a : b;

        return Cheat.builder()
            .wall(wall)
            .p1(p1)
            .p2(p2)
            .win(field.getPathValue(p2) - field.getPathValue(p1) - 2)
            .build();
    }

    public static int countCheats(Map<Integer, Set<Cheat>> cheatMap, int minWin) {
        return cheatMap.keySet().stream()
            .filter(win -> win >= minWin)
            .map(win -> cheatMap.get(win).size())
            .reduce(0, Integer::sum);
    }

    public static void main(String[] args) {
        System.out.println("Small race");
        RaceField field = new RaceField(getFileContent("C:\\learning\\git\\algorithms\\src\\main\\resources\\smallrace.txt"));

        field.calculateRacePath();
        Printer.printPath(field.getPathValues(), field.getWidth(), field.getHeight());
        Printer.printPath(field.getRaceTrack());

        CheatCalculator cheatCalculator = new CheatCalculator(field);
        Map<Integer, Set<Cheat>> cheatMap = cheatCalculator.calculateSmallCheats();
        Printer.printCheats(cheatMap, 1);
        System.out.println("number of small cheats with min win 50: " + countCheats(cheatMap, 50));

        cheatMap = cheatCalculator.getBigCheats(50);
        Printer.printCheats(cheatMap, 50);
        System.out.println("number of big cheats with min win 50: " + countCheats(cheatMap, 50));

        System.out.println("Big race");
        RaceField big = new RaceField(getFileContent("C:\\learning\\git\\algorithms\\src\\main\\resources\\bigrace.txt"));
        big.calculateRacePath();
        cheatCalculator = new CheatCalculator(big);

        cheatMap = cheatCalculator.calculateSmallCheats();
        System.out.println("number of small cheats with min win 100: " + countCheats(cheatMap, 100));

        cheatMap = cheatCalculator.getBigCheats(100);
        System.out.println("number of big cheats with min win 100: " + countCheats(cheatMap, 100));
    }
}
