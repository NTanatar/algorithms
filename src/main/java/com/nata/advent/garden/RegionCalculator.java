package com.nata.advent.garden;

import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegionCalculator {

    private final GardenMap gardenMap;

    public void calculateRegions() {
        Plot p = findUnassignedPlot();
        while (p != null) {
            createRegion(p);
            p = findUnassignedPlot();
        }
    }

    private Plot findUnassignedPlot() {
        for (int y = 0; y < gardenMap.getHeight(); y++) {
            for (int x = 0; x < gardenMap.getWidth(); x++) {
                if (gardenMap.getPlots()[y][x].getRegion() == null) {
                    return gardenMap.getPlots()[y][x];
                }
            }
        }
        return null;
    }

    private void createRegion(Plot p) {
        Region region = new Region(gardenMap.getRegions().size() + 1, p);
        gardenMap.getRegions().add(region);
        expandRegion(region, p);
    }

    private void expandRegion(Region region, Plot start) {
        Set<Plot> candidates = new HashSet<>(getUnassignedNeighborsOfSameType(start));

        while (!candidates.isEmpty()) {
            Set<Plot> newCandidates = new HashSet<>();

            candidates.forEach(candidate -> {
                if (candidate.getRegion() == null) {
                    region.add(candidate);
                    newCandidates.addAll(getUnassignedNeighborsOfSameType(candidate));
                }
            });
            candidates = newCandidates;
        }
    }

    private Set<Plot> getUnassignedNeighborsOfSameType(Plot p) {
        Set<Plot> result = new HashSet<>();
        addIfUnassignedOfSameType(result, gardenMap.getTopNeighbor(p), p.getType());
        addIfUnassignedOfSameType(result, gardenMap.getBottomNeighbor(p), p.getType());
        addIfUnassignedOfSameType(result, gardenMap.getLeftNeighbor(p), p.getType());
        addIfUnassignedOfSameType(result, gardenMap.getRightNeighbor(p), p.getType());
        return result;
    }

    private void addIfUnassignedOfSameType(Set<Plot> plots, Plot p, char type) {
        if (p != null && p.getRegion() == null && p.getType() == type) {
            plots.add(p);
        }
    }
}
