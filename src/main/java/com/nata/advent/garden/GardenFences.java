package com.nata.advent.garden;

import static com.nata.advent.FileUtil.getFileContent;
import static com.nata.advent.garden.Printer.printRegionPrizes;
import static com.nata.advent.garden.Printer.printRegions;

public class GardenFences {

    public static void initGardenMap(GardenMap gardenMap) {
        new FenceCalculator(gardenMap).calculateFences();
        new RegionCalculator(gardenMap).calculateRegions();
    }

    public static int calculateFencePrize(GardenMap gardenMap, PrizeCalculationStrategy strategy) {
        return gardenMap.getRegions().stream()
            .map(strategy::calculatePrize)
            .reduce(0, Integer::sum);
    }

    public static void main(String[] args) {
        GardenMap g1 = new GardenMap(getFileContent("C:\\learning\\git\\algorithms\\src\\main\\resources\\garden1.txt"));
        initGardenMap(g1);
        GardenMap g2 = new GardenMap(getFileContent("C:\\learning\\git\\algorithms\\src\\main\\resources\\garden2.txt"));
        initGardenMap(g2);
        GardenMap g3 = new GardenMap(getFileContent("C:\\learning\\git\\algorithms\\src\\main\\resources\\biggarden.txt"));
        initGardenMap(g3);

        PrizeCalculationStrategy perimeterStrategy = new PrizeCalculationWithPerimeter();
        PrizeCalculationStrategy sidesStrategy = new PrizeCalculationWithSides();

        System.out.println("-------- calculating with perimeter: ");
        printRegions(g1);
        printRegionPrizes(g1, perimeterStrategy);
        System.out.println("garden1: " + calculateFencePrize(g1, perimeterStrategy));

        printRegions(g2);
        printRegionPrizes(g2, perimeterStrategy);
        System.out.println("garden2: " + calculateFencePrize(g2, perimeterStrategy));

        System.out.println("garden3: " + calculateFencePrize(g3, perimeterStrategy));

        System.out.println("-------- calculating with sides: ");
        printRegions(g1);
        printRegionPrizes(g1, sidesStrategy);
        System.out.println("garden1: " + calculateFencePrize(g1, sidesStrategy));

        printRegions(g2);
        printRegionPrizes(g2, sidesStrategy);
        System.out.println("garden2: " + calculateFencePrize(g2, sidesStrategy));

        System.out.println("garden3: " + calculateFencePrize(g3, sidesStrategy));
    }
}
