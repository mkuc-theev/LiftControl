package com.michal.kuc.liftctl.model;


import java.math.BigInteger;

import static com.michal.kuc.liftctl.model.Direction.DOWN;
import static com.michal.kuc.liftctl.model.Direction.UP;


/**
 * Class describing a lift carriage, with methods that allow sending it to different floors
 */
public class Carriage {
    private final BigInteger id;
    private String name;
    private Integer currentFloor;
    private ElevatorQueue targetFloors;

    /**
     * Standard constructor
     * @param id The ID number of the lift carriage, usually provided by the repository service
     * @param name The user-defined name of the lift for use in UI
     */
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

    public Integer getCurrentFloor() {
        return currentFloor;
    }

    public ElevatorQueue getTargetFloors() {
        return targetFloors;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Executes one "step" of the simulation for this carriage, either moving it or doing nothing depending on
     * current state, deletes an entry from the targetFloors queue.
     */
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

    /**
     * Represents the pressing of an elevator call button on one of the floors, checks if the carriage is able to
     * fulfill the call, or if another one should be sent.
     * @param floor The floor at which the carriage was called
     * @param direction The direction in which the user wants to go from the floor they're at
     * @return true if this carriage accepts the call, false otherwise
     */
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

    /**
     * Represents the pressing of a numbered floor button inside the elevator, adding it to an appropriate place
     * in the targetFloors queue.
     * @param floor The floor number where the elevator will be sent
     */
    public void send(Integer floor) {
        if (targetFloors.size() == 0) {
            targetFloors.add(floor);
            return;
        }
        if (targetFloors.contains(floor)) {
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

    /**
     * Provides the current status of the carriage
     * @return String containing carriage ID, name and positional status
     */
    public String status() {
        return String.format("Carriage: ID=%d, name=%s, status=(%d, %s)", id, name, currentFloor, targetFloors);
    }

    private Direction calculateDirection() {
        return targetFloors.peek() > currentFloor ? UP : DOWN;
    }

}
