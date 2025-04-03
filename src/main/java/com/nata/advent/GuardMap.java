package com.nata.advent;

import static com.nata.advent.Direction.toDirection;
import static com.nata.advent.FileUtil.getFileContent;
import static com.nata.advent.FindAWord.countInString;

import java.util.List;

public class GuardMap {

    private final List<String> map;
    private Position position;
    private final int width;
    private final int height;

    public GuardMap(List<String> map) {
        this.map = map;
        this.width = map.getFirst().length();
        this.height = map.size();
        this.position = findStartPosition();
    }

    public long countGuardPositions() {
        if (position == null) {
            return 0;
        }
        guardWalk();

        return map.stream()
            .map(line -> countInString("X", line))
            .reduce(0L, Long::sum);
    }

    private Position findStartPosition() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Direction d = toDirection(map.get(y).charAt(x));
                if (d != null)  {
                    return new Position(x, y, d);
                }
            }
        }
        return null;
    }

    void guardWalk() {
        while (!isExit(position)) {
            markAsVisited(position);
            while (isObstacle(position.getNext())) {
                position.turnRight();
            }
            position = position.getNext();
        }
    }

    void markAsVisited(Position position) {
        StringBuilder builder = new StringBuilder(map.get(position.getY()));
        builder.setCharAt(position.getX(), 'X');
        map.set(position.getY(), builder.toString());
    }

    boolean isExit(Position pos) {
        return pos.getX() < 0 || pos.getX() >= width || pos.getY() < 0 || pos.getY() >= height;
    }

    boolean isObstacle(Position pos) {
        if (isExit(pos)) {
            return false;
        }
        char value = map.get(pos.getY()).charAt(pos.getX());
        return value == '#';
    }

    public static void main(String[] args) {
        GuardMap guardMap = new GuardMap(getFileContent("bigmap.txt"));
        System.out.println(guardMap.countGuardPositions());
    }
}
