package com.michal.kuc.liftctl.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.michal.kuc.liftctl.model.Direction.DOWN;
/**
 * A linked list representing the sequential order of floors to go to, implements extra logic for
 * inserting items in the middle.
 */
public class ElevatorQueue extends LinkedList<Integer> {
    ArrayList<Integer> list;

    public ElevatorQueue() {
        list = new ArrayList<>();
    }

    public ElevatorQueue(Integer... contents) {
        list = (ArrayList<Integer>) List.of(contents);
    }

    /**
     * Inserts given element at the head of the list.
     * @param item the element to add
     */
    public void addFirst(Integer item) {
        list.add(0, item);
    }

    /**
     * Inserts given element at the end of the list.
     * @param item the element to add
     */
    public void addLast(Integer item) {
        list.add(list.size()-1, item);
    }

    /**
     * Inserts a floor number into the queue - dependent on the position of the carriage, the direction it's going
     * and the floors currently present in the queue.
     * @param item The floor number to insert into the queue
     * @param direction The direction in which the carriage is currently going
     */
    public void insertFloor(Integer item, Direction direction) {
        int lastCheckedFloor;
        if (direction.equals(DOWN)) {
            lastCheckedFloor = Integer.MAX_VALUE;
            for (int i = 0; i < list.size(); i++) {
                if (item > list.get(i) || list.get(i) > lastCheckedFloor) {
                    list.add(i, item);
                    return;
                }
                lastCheckedFloor = list.get(i);
            }
            list.add(list.size()-1, item);
        } else {
            lastCheckedFloor = Integer.MIN_VALUE;
            for (int i = 0; i < list.size(); i++) {
                if (item < list.get(i) || list.get(i) < lastCheckedFloor) {
                    list.add(i, item);
                    return;
                }
                lastCheckedFloor = list.get(i);
            }
            list.add(list.size()-1, item);
        }
    }

    /**
     * Returns the first element of the queue.
     * @return The first element of the queue.
     */
    public Integer peek() {
        return list.get(0);
    }

    /**
     * Removes and returns the first element of the queue.
     * @return The first element of the queue.
     */
    public Integer pop() {
        Integer value = list.get(0);
        list.remove(0);
        return value;
    }

}
