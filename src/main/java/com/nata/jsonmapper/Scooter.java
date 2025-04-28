package com.nata.jsonmapper;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Scooter {
    String name;
    private double weight;
    private int height;
    private int length;
    private Wheel frontWheel;
    private Wheel backWheel;
    private List<Color> colors;
}
