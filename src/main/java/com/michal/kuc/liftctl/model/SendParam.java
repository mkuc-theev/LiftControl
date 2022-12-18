package com.michal.kuc.liftctl.model;

/**
 * Information about a floor button press inside an elevator carriage
 */
public class SendParam {
    private Integer floor;

    public SendParam(Integer floor) {
        this.floor = floor;
    }

    public SendParam() {
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }
}
