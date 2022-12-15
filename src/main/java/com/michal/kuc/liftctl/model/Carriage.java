package com.michal.kuc.liftctl.model;


import java.math.BigInteger;

import static com.michal.kuc.liftctl.model.Direction.DOWN;
import static com.michal.kuc.liftctl.model.Direction.UP;



public class Carriage {
    private final BigInteger id;
    private String name;
    private Integer currentFloor;
    private ElevatorQueue targetFloors;

    public Carriage(BigInteger id, String name) {
        this.id = id;
        this.name = name;
        currentFloor = 0;
        targetFloors = new ElevatorQueue();
    }
    public String getName() {
        return name;
    }

    public BigInteger getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void step() {
        if (targetFloors.size() == 0) {
            return;
        }
        Direction currentDirection = calculateDirection();
        if (currentDirection.equals(UP)) {
            currentFloor += 1;
        } else if (currentDirection.equals(DOWN)) {
            currentFloor -= 1;

        }
        if (targetFloors.peek().equals(currentFloor)) {
            targetFloors.remove();
        }
    }

    public Boolean call(Integer floor, Direction direction) {
        if (targetFloors.size() != 0) {
            Direction currentDirection = calculateDirection();
            if (currentDirection.equals(DOWN) && floor > currentFloor) {
                return false;
            }
            if (currentDirection.equals(UP) && floor < currentFloor) {
                return false;
            }
            if (!currentDirection.equals(direction)) {
                return false;
            }
            targetFloors.insertFloor(floor, direction);
            return true;
        }
        targetFloors.add(floor);
        return true;
    }

    public void send(Integer floor) {
        if (targetFloors.size() == 0) {
            targetFloors.add(floor);
            return;
        }
        Direction direction = calculateDirection();
        if (direction.equals(UP)) {
            if (floor < currentFloor) {
                targetFloors.addLast(floor);
                return;
            }
            targetFloors.insertFloor(floor, direction);
        }
        if (direction.equals(DOWN)){
            if (floor > currentFloor) {
                targetFloors.addLast(floor);
                return;
            }
            targetFloors.insertFloor(floor, direction);
        }

    }
    public String status() {
        return String.format("Carriage: ID=%d, name=%s, status=(%d, %s)", id, name, currentFloor, targetFloors);
    }

    private Direction calculateDirection() {
        return targetFloors.peek() > currentFloor ? UP : DOWN;
    }

}
