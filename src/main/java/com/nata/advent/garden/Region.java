package com.nata.advent.garden;

import java.util.HashSet;
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

    void add(Plot p) {
        plots.add(p);
        p.setRegion(this);
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
