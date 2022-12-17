package com.michal.kuc.liftctl.service;

import com.michal.kuc.liftctl.model.CallParams;
import com.michal.kuc.liftctl.model.Carriage;
import com.michal.kuc.liftctl.model.CarriageInfo;
import com.michal.kuc.liftctl.model.SendParam;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

@Service
public class CarriageService {
    private final List<Carriage> carriages = new ArrayList<>();
    private final Queue<CallParams> callParamsQueue = new LinkedList<>();

    public void initialize(List<CarriageInfo> carriageInfos) {
        removeAllCarriages();
        for (CarriageInfo carInfo : carriageInfos) {
            addCarriage(carInfo);
        }
        System.out.println("Found: " + carriages);
    }

    public void call(CallParams callParams) {
        callParamsQueue.add(callParams);
        System.out.println("Current call queue: " + callParamsQueue);
        attemptCalls();
    }

    public void send(BigInteger id, SendParam floor) {
        for (Carriage car : carriages) {
            if (car.getId().equals(id)) {
                car.send(floor);
            }
        }
    }

    public List<Carriage> getAllCarriages() {
        return carriages;
    }

    public Carriage addCarriage(CarriageInfo carriageInfo) {
        Carriage carriage = new Carriage(carriageInfo.getId(), carriageInfo.getName());
        carriages.add(carriage);
        return carriage;
    }

    public void removeCarriage(BigInteger id) {
        carriages.removeIf(carriage -> Objects.equals(id, carriage.getId()));
    }

    public Optional<Carriage> updateCarriageById(CarriageInfo carriageInfo, BigInteger id) {
        for (Carriage car : carriages) {
            if (car.getId().equals(id)) {
                car.setName(carriageInfo.getName());
                return Optional.of(car);
            }
        }
        return Optional.empty();
    }

    public void removeAllCarriages() {
        carriages.removeAll(carriages);
    }

    public void attemptCalls() {
        List<CallParams> tmpCallParamsQueue = List.copyOf(callParamsQueue);
        for (CallParams call : tmpCallParamsQueue) {
            executeCall(call);
        }
    }
    public void step() {
        System.out.println("Beginning call execution on queue: " + callParamsQueue + "...");
        attemptCalls();
        System.out.println("Executing steps for all carriages...");
        for (Carriage car : carriages) {
            car.step();
        }
    }

    private void executeCall(CallParams callParams) {
        for (Carriage car : carriages) {
            if (car.call(callParams.getFloor(), callParams.getDirection())) {
                System.out.println("Executing call on lift " + car.getId() + "...");
                callParamsQueue.remove(callParams);
                return;
            }
        }
    }
}
