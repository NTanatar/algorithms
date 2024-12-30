package com.nata.advent.garden;

import static java.util.Optional.ofNullable;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class GardenMap {

    private final Plot[][] plots;
    private final int width;
    private final int height;
    private final List<Region> regions = new ArrayList<>();

    public static Character getType(Plot p) {
        return ofNullable(p)
            .map(Plot::getType)
            .orElse(null);
    }

    public GardenMap(List<String> data) {
        this.height = data.size();
        this.width = data.getFirst().length();
        this.plots = new Plot[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                plots[y][x] = new Plot(x, y, data.get(y).charAt(x));
            }
        }
    }

    public boolean isLeftBorder(Plot p) {
        return p.getX() == 0;
    }

    public boolean isRightBorder(Plot p) {
        return p.getX() == width - 1;
    }

    public boolean isTopBorder(Plot p) {
        return p.getY() == 0;
    }

    public boolean isBottomBorder(Plot p) {
        return p.getY() == height - 1;
    }

    public Plot getTopNeighbor(Plot p) {
        return isTopBorder(p)
            ? null
            : plots[p.getY() - 1][p.getX()];
    }

    public Plot getBottomNeighbor(Plot p) {
        return isBottomBorder(p)
            ? null
            : plots[p.getY() + 1][p.getX()];
    }

    public Plot getLeftNeighbor(Plot p) {
        return isLeftBorder(p)
            ? null
            : plots[p.getY()][p.getX() - 1];
    }

    public Plot getRightNeighbor(Plot p) {
        return isRightBorder(p)
            ? null
            : plots[p.getY()][p.getX() + 1];
    }
}
