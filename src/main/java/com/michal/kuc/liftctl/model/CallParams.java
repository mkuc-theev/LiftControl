package com.michal.kuc.liftctl.model;

public class CallParams {
    private Integer floor;
    private Direction direction;

    public CallParams() {}
    public CallParams(Integer floor, Direction direction) {
        this.direction = direction;
        this.floor = floor;
    }
    public Direction getDirection() {
        return direction;
    }
    public Integer getFloor() {
        return floor;
    }
}
