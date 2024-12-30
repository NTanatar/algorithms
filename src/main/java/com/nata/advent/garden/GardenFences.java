package com.nata.advent.garden;

import static com.nata.advent.FileUtil.getFileContent;
import static com.nata.advent.garden.Printer.printRegionPrizesWithPerimeter;
import static com.nata.advent.garden.Printer.printRegionPrizesWithSides;
import static com.nata.advent.garden.Printer.printRegions;

public class GardenFences {

    public static void initGardenMap(GardenMap gardenMap) {
        new FenceCalculator(gardenMap).calculateFences();
        new RegionCalculator(gardenMap).calculateRegions();
    }

    public static int calculateFencePrizesWithPerimeter(GardenMap gardenMap) {
        return gardenMap.getRegions().stream()
            .map(PrizeCalculator::calculatePrizeWithPerimeter)
            .reduce(0, Integer::sum);
    }

    public static int calculateFencePrizesWithSides(GardenMap gardenMap) {
        return gardenMap.getRegions().stream()
            .map(PrizeCalculator::calculatePrizeWithSides)
            .reduce(0, Integer::sum);
    }


    public static void main(String[] args) {
        GardenMap g1 = new GardenMap(getFileContent("C:\\learning\\git\\algorithms\\src\\main\\resources\\garden1.txt"));
        initGardenMap(g1);
        GardenMap g2 = new GardenMap(getFileContent("C:\\learning\\git\\algorithms\\src\\main\\resources\\garden2.txt"));
        initGardenMap(g2);
        GardenMap g3 = new GardenMap(getFileContent("C:\\learning\\git\\algorithms\\src\\main\\resources\\biggarden.txt"));
        initGardenMap(g3);

        System.out.println("-------- calculating with perimeter: ");
        printRegions(g1);
        printRegionPrizesWithPerimeter(g1);
        System.out.println("garden1: " + calculateFencePrizesWithPerimeter(g1));

        printRegions(g2);
        printRegionPrizesWithPerimeter(g2);
        System.out.println("garden2: " + calculateFencePrizesWithPerimeter(g2));

        System.out.println("garden3: " + calculateFencePrizesWithPerimeter(g3));

        System.out.println("-------- calculating with sides: ");
        printRegions(g1);
        printRegionPrizesWithSides(g1);
        System.out.println("garden1: " + calculateFencePrizesWithSides(g1));

        printRegions(g2);
        printRegionPrizesWithSides(g2);
        System.out.println("garden2: " + calculateFencePrizesWithSides(g2));

        System.out.println("garden3: " + calculateFencePrizesWithSides(g3));
    }
}
