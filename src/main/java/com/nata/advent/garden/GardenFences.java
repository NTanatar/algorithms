package com.nata.advent.garden;

import static com.nata.advent.Direction.DOWN;
import static com.nata.advent.Direction.LEFT;
import static com.nata.advent.Direction.RIGHT;
import static com.nata.advent.Direction.UP;
import static com.nata.advent.FileUtil.getFileContent;
import static com.nata.advent.garden.FenceDirection.EAST_WEST;
import static com.nata.advent.garden.FenceDirection.NORTH_SOUTH;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
            .map(this::calculateFencePrizeWithDiscount)
            .reduce(0, Integer::sum);
    }

    private void calculateFences() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                calculateFences(plots[y][x]);
            }
        }
    }

    private void calculateFences(Plot p) {
        if (!Objects.equals(getType(getLeftNeighbor(p)), p.getType())) {
            p.addFence(LEFT);
        }
        if (!Objects.equals(getType(getRightNeighbor(p)), p.getType())) {
            p.addFence(RIGHT);
        }
        if (!Objects.equals(getType(getTopNeighbor(p)), p.getType())) {
            p.addFence(UP);
        }
        if (!Objects.equals(getType(getBottomNeighbor(p)), p.getType())) {
            p.addFence(DOWN);
        }
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

    private int calculateFencePrizeWithDiscount(Region r) {
        int area = r.getPlots().size();
        int numSides = calculateNumSides(r);
        int prize = area * numSides;
        System.out.println("Region " + r.getId() + " of " + r.getType()
            + ": area = " + area + ", numSides = " + numSides + " -> prize = " + prize);
        return prize;
    }

    private int calculateNumSides(Region r) {
        Map<FenceDirection, List<FencePart>> byDirection = r.getPlots().stream()
            .flatMap(p -> p.getFences().stream())
            .collect(groupingBy(FencePart::getType));

        return calculateNumSides(byDirection.get(EAST_WEST)) + calculateNumSides(byDirection.get(NORTH_SOUTH));
    }

    private static int calculateNumSides(List<FencePart> fences) {
        Map<String, List<Integer>> byLineId = fences.stream()
            .collect(groupingBy(FencePart::getLineId, mapping(FencePart::getConstantValue, toList())));

        return byLineId.values().stream()
            .map(list -> countGaps(list) + 1)
            .reduce(0, Integer::sum);
    }

    private static int countGaps(List<Integer> values) {
        if (values.size() <= 1) {
            return 0;
        }
        int result = 0;
        Collections.sort(values);
        for (int i = 1; i < values.size(); i++) {
            if (values.get(i) > values.get(i - 1) + 1) {
                result++;
            }
        }
        return result;
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

    private Character getType(Plot p) {
        return ofNullable(p)
            .map(Plot::getType)
            .orElse(null);
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
