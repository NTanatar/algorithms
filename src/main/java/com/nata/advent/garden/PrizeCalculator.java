package com.nata.advent.garden;

public class PrizeCalculator {

    public static int calculatePrizeWithPerimeter(Region r) {
        return r.getArea() * r.getPerimeter();
    }

    public static int calculatePrizeWithSides(Region r) {
        return r.getArea() * r.getNumSides();
    }
}
