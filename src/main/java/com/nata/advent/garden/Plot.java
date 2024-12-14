package com.nata.advent.garden;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Plot {
    private int x;
    private int y;
    private char type;
    private int numFences;
    private Region region;

    public Plot(int x, int y, char type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.numFences = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Plot that = (Plot) o;
        return x == that.x && y == that.y;
    }

    @Override
    public String toString() {
        return "{(" + x + "," + y + "),t=" + type + ",r=" + region + "}";
    }
}
