package com.nata.advent.garden;

public class PrizeCalculationWithPerimeter implements PrizeCalculationStrategy {

    @Override
    public int calculatePrize(Region r) {
        return r.getArea() * r.getPerimeter();
    }
}
