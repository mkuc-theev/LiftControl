package com.michal.kuc.liftctl.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.michal.kuc.liftctl.model.Direction.DOWN;

public class ElevatorQueue extends LinkedList<Integer> {
    ArrayList<Integer> list;

    public ElevatorQueue() {
        list = new ArrayList<>();
    }

    public ElevatorQueue(Integer... contents) {
        list = (ArrayList<Integer>) List.of(contents);
    }

    public void addFirst(Integer item) {
        list.add(0, item);
    }

    public void addLast(Integer item) {
        list.add(list.size()-1, item);
    }

    public void insertFloor(Integer item, Direction direction) {
        LinkedList<Integer> tmpList = new LinkedList<>();
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
    public Integer peek() {
        return list.get(0);
    }

    public Integer pop() {
        Integer value = list.get(0);
        list.remove(0);
        return value;
    }

}
