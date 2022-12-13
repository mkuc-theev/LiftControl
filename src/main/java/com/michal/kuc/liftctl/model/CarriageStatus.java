package com.michal.kuc.liftctl.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "lift-statuses")
public class CarriageStatus {
    @Id
    private Integer id;
    private Integer currentFloor;
    private Integer targetFloor;

    public CarriageStatus() {}

    public CarriageStatus(Integer id, Integer currentFloor, Integer targetFloor) {
        this.id = id;
        this.currentFloor = currentFloor;
        this.targetFloor = targetFloor;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCurrentFloor() {
        return currentFloor;
    }

    public Integer getTargetFloor() {
        return targetFloor;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d, %d)", id, currentFloor, targetFloor);
    }
}
