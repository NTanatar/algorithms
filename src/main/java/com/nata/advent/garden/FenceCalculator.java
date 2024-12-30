package com.nata.advent.garden;

import static com.nata.advent.Direction.DOWN;
import static com.nata.advent.Direction.LEFT;
import static com.nata.advent.Direction.RIGHT;
import static com.nata.advent.Direction.UP;
import static com.nata.advent.garden.GardenMap.getType;

import java.util.Objects;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FenceCalculator {

    private final GardenMap gardenMap;

    public void calculateFences() {
        for (int y = 0; y < gardenMap.getHeight(); y++) {
            for (int x = 0; x < gardenMap.getWidth(); x++) {
                calculateFences(gardenMap.getPlots()[y][x]);
            }
        }
    }

    public void calculateFences(Plot p) {
        if (!Objects.equals(getType(gardenMap.getLeftNeighbor(p)), p.getType())) {
            p.addFence(LEFT);
        }
        if (!Objects.equals(getType(gardenMap.getRightNeighbor(p)), p.getType())) {
            p.addFence(RIGHT);
        }
        if (!Objects.equals(getType(gardenMap.getTopNeighbor(p)), p.getType())) {
            p.addFence(UP);
        }
        if (!Objects.equals(getType(gardenMap.getBottomNeighbor(p)), p.getType())) {
            p.addFence(DOWN);
        }
    }
}
