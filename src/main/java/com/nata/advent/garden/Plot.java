package com.nata.advent.garden;

import com.nata.advent.Direction;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Plot {
    private int x;
    private int y;
    private char type;
    private Region region;
    private List<FencePart> fences;

    public Plot(int x, int y, char type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.fences = new ArrayList<>();
    }

    public void addFence(Direction d) {
        this.fences.add(new FencePart(this, d));
    }

    public int getNumFences() {
        return fences.size();
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
