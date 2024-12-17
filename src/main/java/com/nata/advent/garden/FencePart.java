package com.nata.advent.garden;

import static com.nata.advent.garden.FenceDirection.EAST_WEST;
import static com.nata.advent.garden.FenceDirection.NORTH_SOUTH;

import com.nata.advent.Direction;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
public class FencePart {
    private int constantValue;
    private String lineId;
    private FenceDirection type;

    public FencePart(Plot p, Direction direction) {
        switch (direction) {
            case UP: // y-1 -> y
                this.type = EAST_WEST;
                this.lineId = p.getY() - 1 + "-" + p.getY();
                this.constantValue = p.getX();
                break;
            case DOWN: // y-> y + 1
                this.type = EAST_WEST;
                this.lineId = p.getY() + "-" + p.getY() + 1;
                this.constantValue = p.getX();
                break;
            case LEFT: // x-1 -> x
                this.type = NORTH_SOUTH;
                this.lineId = p.getX() - 1 + "-" + p.getX();
                this.constantValue = p.getY();
                break;
            case RIGHT: // x -> x + 1
                this.type = NORTH_SOUTH;
                this.lineId = p.getX() + "-" + p.getX() + 1;
                this.constantValue = p.getY();
                break;
        }
    }
}
