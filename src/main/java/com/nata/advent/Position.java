package com.nata.advent;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Position {
    private int x;
    private int y;
    private Direction direction;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void turnRight() {
        direction = direction.turnRight();
    }

    public Position getNext() {
        return switch (direction) {
            case UP -> new Position(x, y - 1, direction);
            case DOWN -> new Position(x, y + 1, direction);
            case LEFT -> new Position(x - 1, y, direction);
            case RIGHT -> new Position(x + 1, y, direction);
        };
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
