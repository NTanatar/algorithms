package com.nata.advent.garden;

public class PrizeCalculationWithSides implements PrizeCalculationStrategy {

    @Override
    public int calculatePrize(Region r) {
        return r.getArea() * r.getNumSides();
    }
}
