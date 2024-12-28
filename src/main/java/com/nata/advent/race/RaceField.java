package com.nata.advent.race;

import com.nata.advent.Position;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class RaceField {
    private final List<String> map;
    private final int width;
    private final int height;
    private final Integer [][] pathValues;
    private final List<Position> raceTrack;

    public RaceField(List<String> map) {
        this.map = map;
        width = map.getFirst().length();
        height = map.size();
        pathValues = new Integer[width][height];
        raceTrack = new ArrayList<>();
    }

    public void calculateRacePath() {
        Position p = findStart();
        raceTrack.add(p);

        int numSteps = 0;
        pathValues[p.getX()][p.getY()] = numSteps;

        while (getMap(p) != 'E') {
            p = findNext(p);
            raceTrack.add(p);
            pathValues[p.getX()][p.getY()] = ++numSteps;
        }
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

    int getPathValue(Position p) {
        return pathValues[p.getX()][p.getY()];
    }

    char getMap(int x, int y) {
        return map.get(y).charAt(x);
    }

    private char getMap(Position p) {
        return getMap(p.getX(), p.getY());
    }

    private boolean isFree(int x, int y) {
        return isFree(getMap(x,y)) && pathValues[x][y] == null;
    }

    private boolean isFree(char c) {
        return c == '.' || c == 'E';
    }
}
