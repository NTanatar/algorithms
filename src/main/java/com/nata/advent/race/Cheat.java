package com.nata.advent.race;

import com.nata.advent.Position;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Cheat {
    private Position p1;
    private Position wall;
    private Position p2;
    private int win;
}
