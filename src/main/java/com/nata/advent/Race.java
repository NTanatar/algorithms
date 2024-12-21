package com.nata.advent;

import static com.nata.advent.FileUtil.getFileContent;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

public class Race {

    private final List<String> map;
    private final Integer [][] racePath;
    private final int width;
    private final int height;

    @Getter
    @Setter
    @EqualsAndHashCode
    static class Cheat {
        private Position p1;
        private Position wall;
        private Position p2;
        private int win;
    }

    public Race(List<String> map) {
        this.map = map;
        width = map.getFirst().length();
        height = map.size();
        racePath = new Integer[width][height];
    }

    public void calculateRacePath() {
        Position p = findStart();

        int numSteps = 0;
        racePath[p.getX()][p.getY()] = numSteps;

        while (getMap(p) != 'E') {
            p = findNext(p);
            racePath[p.getX()][p.getY()] = ++numSteps;
        }
    }

    public Map<Integer, Set<Cheat>> calculateAllCheats() {
        List<Cheat> cheats = new ArrayList<>();

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                cheats.addAll(getCheatsForWall(x, y));
            }
        }
        return cheats.stream().collect(groupingBy(Cheat::getWin, toSet()));
    }

    private Position findStart() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (getMap(x,y) == 'S') {
                    return new Position(x, y);
                }
            }
        }
        throw new RuntimeException("No start position found");
    }

    private Position findNext(Position p) {
        if (p.getX() > 0 && isFree(p.getX() - 1, p.getY())) {
            return new Position(p.getX() - 1, p.getY());
        }
        if (p.getY() > 0 && isFree(p.getX(), p.getY() - 1)) {
            return new Position(p.getX(), p.getY() - 1);
        }
        if (p.getX() < width - 1 && isFree(p.getX() + 1, p.getY())) {
            return new Position(p.getX() + 1, p.getY());
        }
        if (p.getY() < height - 1 && isFree(p.getX(), p.getY() + 1)) {
            return new Position(p.getX(), p.getY() + 1);
        }
        throw new RuntimeException("No next position found");
    }

    private List<Cheat> getCheatsForWall(int x, int y) {
        List<Cheat> cheats = new ArrayList<>();
        if (getMap(x,y) != '#') {
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
        return racePath[x - 1][y] != null && racePath[x + 1][y] != null;
    }

    private boolean canCheatVertically(int x, int y) {
        return racePath[x][y - 1] != null && racePath[x][y + 1] != null;
    }

    private Cheat createHorizontalCheat(int x, int y) {
        return createCheat(new Position(x, y), new Position(x + 1, y), new Position(x - 1, y));
    }

    private Cheat createVerticalCheat(int x, int y) {
        return createCheat(new Position(x, y), new Position(x, y - 1), new Position(x, y + 1));
    }

    private Cheat createCheat(Position wall, Position a, Position b) {
        Cheat cheat = new Cheat();
        cheat.wall = wall;

        if (getPathValue(a) < getPathValue(b)) {
            cheat.p1 = a;
            cheat.p2 = b;
            cheat.win = getPathValue(b) - getPathValue(a) - 2;
        } else {
            cheat.p1 = b;
            cheat.p2 = a;
            cheat.win = getPathValue(a) - getPathValue(b) - 2;
        }
        return cheat;
    }

    private int getPathValue(Position p) {
        return racePath[p.getX()][p.getY()];
    }

    private char getMap(int x, int y) {
        return map.get(y).charAt(x);
    }

    private char getMap(Position p) {
        return getMap(p.getX(), p.getY());
    }

    private boolean isFree(int x, int y) {
        return isFree(getMap(x,y)) && racePath[x][y] == null;
    }

    private boolean isFree(char c) {
        return c == '.' || c == 'E';
    }

    public void printRacePath() {
        for (int y = 0; y < height; y++) {
            System.out.println();
            for (int x = 0; x < width; x++) {
                Integer pathValue = racePath[x][y];
                if (pathValue != null)
                    System.out.print(String.format("%02d", pathValue));
                else {
                    System.out.print("  ");
                }
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Race race = new Race(getFileContent("C:\\learning\\git\\algorithms\\src\\main\\resources\\bigrace.txt"));

        race.calculateRacePath();
        //race.printRacePath();
        //System.out.println("--------------------------");

        Map<Integer, Set<Cheat>> cheatMap = race.calculateAllCheats();

        cheatMap.keySet().stream()
            .sorted().
            forEach(win -> System.out.println("win: " + win + ", " + cheatMap.get(win).size() + " cheats"));

        System.out.println("--------------------------");

        int result = cheatMap.keySet().stream()
            .filter(win -> win >= 100)
            .map(win -> cheatMap.get(win).size())
            .reduce(0, Integer::sum);
        System.out.println(result);
    }
}
