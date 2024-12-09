package com.nata.advent;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    public static Direction toDirection(char c) {
        return switch (c) {
            case '^' -> UP;
            case 'v' -> DOWN;
            case '<' -> LEFT;
            case '>' -> RIGHT;
            default -> null;
        };
    }

    public Direction turnRight() {
        return switch (this) {
            case UP -> RIGHT;
            case RIGHT -> DOWN;
            case DOWN -> LEFT;
            case LEFT -> UP;
        };
    }
}
