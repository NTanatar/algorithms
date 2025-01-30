package com.nata.advent.garden;

import static java.util.Optional.ofNullable;

public class Printer {

    public static void printNumFences(GardenMap map) {
        System.out.println("Count fences:");
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                System.out.print(map.getPlots()[y][x].getNumFences());
            }
            System.out.println();
        }
    }

    public static void printRegions(GardenMap map) {
        System.out.println("Regions:");
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                System.out.print(ofNullable(map.getPlots()[y][x].getRegion())
                    .map(r -> String.format("%02d", r.getId()))
                    .orElse("-"));
            }
            System.out.println();
        }
    }

    public static void printRegionPrizes(GardenMap map, PrizeCalculationStrategy strategy) {
        System.out.println("RegionPrizes with Strategy " + strategy);
        map.getRegions().forEach(r ->
            System.out.println("Region " + r.getId() + " of " + r.getType()
                + ": area = " + r.getArea() + ", perimeter = " + r.getPerimeter() + ", sides = " + r.getNumSides()
                + " -> prize = "  + strategy.calculatePrize(r)));
    }
}
