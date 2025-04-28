package com.nata.jsonmapper;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Wheel {
    private int diameter;
    private boolean solid;
    private Color color;
    private String[] tags;
}
