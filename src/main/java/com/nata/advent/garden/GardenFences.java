package com.nata.advent.garden;

import static com.nata.advent.FileUtil.getFileContent;
import static java.util.Optional.ofNullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GardenFences {

    private final Plot[][] plots;
    private final int width;
    private final int height;
    List<Region> regions = new ArrayList<>();

    public GardenFences(List<String> data) {
        this.height = data.size();
        this.width = data.getFirst().length();
        this.plots = new Plot[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                plots[y][x] = new Plot(x, y, data.get(y).charAt(x));
            }
        }
    }

    public int calculateFencePrizes() {
        calculateFences();
        calculateRegions();
        return regions.stream()
            .map(this::calculateFencePrize)
            .reduce(0, Integer::sum);
    }

    private void calculateFences() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                plots[y][x].setNumFences(calculateFences(plots[y][x]));
            }
        }
    }

    private int calculateFences(Plot p) {
        int result = 0;
        if (isLeftBorder(p) || getLeftNeighbor(p).getType() != p.getType()) {
            result++;
        }
        if (isRightBorder(p) || getRightNeighbor(p).getType() != p.getType()) {
            result++;
        }
        if (isTopBorder(p) || getTopNeighbor(p).getType() != p.getType()) {
            result++;
        }
        if (isBottomBorder(p) || getBottomNeighbor(p).getType() != p.getType()) {
            result++;
        }
        return result;
    }

    private void calculateRegions() {
        Plot p = findUnassignedPlot();
        while (p != null) {
            createRegion(p);
            p = findUnassignedPlot();
        }
    }

    private Plot findUnassignedPlot() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (plots[y][x].getRegion() == null) {
                    return plots[y][x];
                }
            }
        }
        return null;
    }

    private void createRegion(Plot p) {
        Region region = new Region(regions.size() + 1, p);
        regions.add(region);
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
        addIfUnassignedOfSameType(result, getTopNeighbor(p), p.getType());
        addIfUnassignedOfSameType(result, getBottomNeighbor(p), p.getType());
        addIfUnassignedOfSameType(result, getLeftNeighbor(p), p.getType());
        addIfUnassignedOfSameType(result, getRightNeighbor(p), p.getType());
        return result;
    }

    private void addIfUnassignedOfSameType(Set<Plot> plots, Plot p, char type) {
        if (p != null && p.getRegion() == null && p.getType() == type) {
            plots.add(p);
        }
    }

    private int calculateFencePrize(Region r) {
        int area = r.getPlots().size();
        int perimeter = r.getPlots().stream()
            .map(Plot::getNumFences)
            .reduce(0, Integer::sum);
        int prize = area * perimeter;
        System.out.println("Region " + r.getId() + " of " + r.getType()
            + ": area = " + area + ", perimeter = " + perimeter + " -> prize = " + prize);
        return prize;
    }

    private boolean isLeftBorder(Plot p) {
        return p.getX() == 0;
    }

    private boolean isRightBorder(Plot p) {
        return p.getX() == width - 1;
    }

    private boolean isTopBorder(Plot p) {
        return p.getY() == 0;
    }

    private boolean isBottomBorder(Plot p) {
        return p.getY() == height - 1;
    }

    private Plot getTopNeighbor(Plot p) {
        return isTopBorder(p)
            ? null
            : plots[p.getY() - 1][p.getX()];
    }

    private Plot getBottomNeighbor(Plot p) {
        return isBottomBorder(p)
            ? null
            : plots[p.getY() + 1][p.getX()];
    }

    private Plot getLeftNeighbor(Plot p) {
        return isLeftBorder(p)
            ? null
            : plots[p.getY()][p.getX() - 1];
    }

    private Plot getRightNeighbor(Plot p) {
        return isRightBorder(p)
            ? null
            : plots[p.getY()][p.getX() + 1];
    }

    private void printNumFences() {
        System.out.println("Count fences:");
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(plots[y][x].getNumFences());
            }
            System.out.println();
        }
    }

    private void printRegions() {
        System.out.println("Regions:");
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(ofNullable(plots[y][x].getRegion())
                    .map(r -> String.valueOf(r.getId()))
                    .orElse("-"));
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        GardenFences f = new GardenFences(getFileContent("C:\\learning\\git\\algorithms\\src\\main\\resources\\garden1.txt"));
        System.out.println("garden1: " + f.calculateFencePrizes());

        System.out.println("--------");
        GardenFences f2 = new GardenFences(getFileContent("C:\\learning\\git\\algorithms\\src\\main\\resources\\garden2.txt"));
        System.out.println("garden2: " + f2.calculateFencePrizes());

        System.out.println("--------");
        GardenFences f3 = new GardenFences(getFileContent("C:\\learning\\git\\algorithms\\src\\main\\resources\\biggarden.txt"));
        System.out.println("garden 3: " + f3.calculateFencePrizes());
    }

}
