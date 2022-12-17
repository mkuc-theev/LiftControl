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

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    @Override
    public String toString() {
        return String.format("Floor: %d, Direction: %s", floor, direction);
    }
}
