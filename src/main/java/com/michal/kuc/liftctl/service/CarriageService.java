package com.michal.kuc.liftctl.service;

import com.michal.kuc.liftctl.model.CallParams;
import com.michal.kuc.liftctl.model.Carriage;
import com.michal.kuc.liftctl.model.CarriageInfo;
import com.michal.kuc.liftctl.model.SendParam;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

/**
 * A service that stores and manages the carriages and calls made in the system
 */
@Service
public class CarriageService {
    private final List<Carriage> carriages = new ArrayList<>();
    private final Queue<CallParams> callParamsQueue = new LinkedList<>();

    /**
     * Initializes the carriage list with data from the database
     *
     * @param carriageInfos Information about carriages stored in the database
     */
    public void initialize(List<CarriageInfo> carriageInfos) {
        removeAllCarriages();
        for (CarriageInfo carInfo : carriageInfos) {
            addCarriage(carInfo);
        }
    }

    /**
     * Adds an elevetor call request to the queue
     *
     * @param callParams Information about the floor at which the call was made and intended direction
     */
    public void call(CallParams callParams) {
        callParamsQueue.add(callParams);
        attemptCalls();
    }

    /**
     * Sends a carriage to specified floor
     *
     * @param id    ID of the carriage to target
     * @param floor Floor to send the specified carriage to
     */
    public void send(BigInteger id, SendParam floor) {
        for (Carriage car : carriages) {
            if (car.getId().equals(id)) {
                car.send(floor);
            }
        }
    }

    /**
     * Fetches the list of carriages that the service is aware of
     *
     * @return List of currently stored carriages
     */
    public List<Carriage> getAllCarriages() {
        return carriages;
    }


    /**
     * Fetches the list of calls that the service is aware of
     *
     * @return List representing current state of the call queue
     */
    public List<CallParams> getAllCalls() {
        return callParamsQueue.stream().toList();
    }

    /**
     * Adds a new carriage to the list
     *
     * @param carriageInfo Information about the carriage received from the database
     * @return New Carriage object
     */
    public Carriage addCarriage(CarriageInfo carriageInfo) {
        Carriage carriage = new Carriage(carriageInfo.getId(), carriageInfo.getName());
        carriages.add(carriage);
        return carriage;
    }

    /**
     * Removes specified carriage from the list
     *
     * @param id ID of carriage to be removed
     */
    public void removeCarriage(BigInteger id) {
        carriages.removeIf(carriage -> Objects.equals(id, carriage.getId()));
    }

    /**
     * Updates specified carriage's name
     *
     * @param carriageInfo Information object containing the new name
     * @param id           ID of carriage to be renamed
     * @return Newly renamed carriage object
     */
    public Optional<Carriage> updateCarriageById(CarriageInfo carriageInfo, BigInteger id) {
        for (Carriage car : carriages) {
            if (car.getId().equals(id)) {
                car.setName(carriageInfo.getName());
                return Optional.of(car);
            }
        }
        return Optional.empty();
    }

    /**
     * Clears the list of carriages
     */
    public void removeAllCarriages() {
        carriages.removeAll(carriages);
    }

    /**
     * Performs evaluation of all calls in the queue
     */
    public void attemptCalls() {
        List<CallParams> tmpCallParamsQueue = List.copyOf(callParamsQueue);
        for (CallParams call : tmpCallParamsQueue) {
            executeCall(call);
        }
    }

    /**
     * Steps the simulation forward, including call processing and elevator movement
     */
    public void step() {
        attemptCalls();
        for (Carriage car : carriages) {
            car.step();
        }
    }

    /**
     * Evaluates a call's ability to be taken by one of the carriages from the list, executes the command if
     * a suitable carriage is found
     *
     * @param callParams Information about the floor at which the call was made and intended direction
     */
    private void executeCall(CallParams callParams) {
        for (Carriage car : carriages) {
            if (car.call(callParams.getFloor(), callParams.getDirection())) {
                callParamsQueue.remove(callParams);
                return;
            }
        }
    }
}
