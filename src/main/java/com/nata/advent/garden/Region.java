package com.nata.advent.garden;

import static com.nata.advent.garden.FenceDirection.EAST_WEST;
import static com.nata.advent.garden.FenceDirection.NORTH_SOUTH;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Region {
    private char type;
    private int id;
    private Set<Plot> plots;

    public Region(int id, Plot p) {
        this.id = id;
        this.plots = new HashSet<>();
        this.type = p.getType();
        plots.add(p);
        p.setRegion(this);
    }

    public void add(Plot p) {
        plots.add(p);
        p.setRegion(this);
    }

    public int getPerimeter() {
        return plots.stream()
            .map(Plot::getNumFences)
            .reduce(0, Integer::sum);
    }

    public int getArea() {
        return plots.size();
    }

    public int getNumSides() {
        Map<FenceDirection, List<FencePart>> byDirection = plots.stream()
            .flatMap(p -> p.getFences().stream())
            .collect(groupingBy(FencePart::getType));

        return calculateNumSides(byDirection.get(EAST_WEST)) + calculateNumSides(byDirection.get(NORTH_SOUTH));
    }

    private int calculateNumSides(List<FencePart> fences) {
        Map<String, List<Integer>> byLineId = fences.stream()
            .collect(groupingBy(FencePart::getLineId, mapping(FencePart::getConstantValue, toList())));

        return byLineId.values().stream()
            .map(list -> countGaps(list) + 1)
            .reduce(0, Integer::sum);
    }

    private int countGaps(List<Integer> values) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Region that = (Region) o;
        return id == that.getId();
    }
}
