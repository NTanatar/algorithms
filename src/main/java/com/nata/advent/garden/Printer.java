package com.nata.advent.garden;

import static com.nata.advent.garden.PrizeCalculator.calculatePrizeWithPerimeter;
import static com.nata.advent.garden.PrizeCalculator.calculatePrizeWithSides;
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

    public static void printRegionPrizesWithPerimeter(GardenMap map) {
        System.out.println("RegionPrizes with perimeter:");
        map.getRegions().forEach(r ->
            System.out.println("Region " + r.getId() + " of " + r.getType()
                + ": area = " + r.getArea() + ", perimeter = " + r.getPerimeter() + " -> prize = "
                + calculatePrizeWithPerimeter(r)));
    }

    public static void printRegionPrizesWithSides(GardenMap map) {
        System.out.println("RegionPrizes with sides:");
        map.getRegions().forEach(r ->
            System.out.println("Region " + r.getId() + " of " + r.getType()
                + ": area = " + r.getArea() + ", sides = " + r.getNumSides() + " -> prize = "
                + calculatePrizeWithSides(r)));
    }
}
