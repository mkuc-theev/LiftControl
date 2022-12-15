package com.michal.kuc.liftctl.service;

import com.michal.kuc.liftctl.model.CallParams;
import com.michal.kuc.liftctl.model.Carriage;
import com.michal.kuc.liftctl.model.CarriageInfo;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

@Service
public class CarriageService {
    private final List<Carriage> carriages = new ArrayList<>();
    private final Queue<CallParams> callParamsQueue = new LinkedList<>();

    public Optional<Carriage> getCarriageById(BigInteger id) {
        for (Carriage car : carriages) {
            if (car.getId().equals(id)) {
                return Optional.of(car);
            }
        }
        return Optional.empty();
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

    public void step() {
        for (CallParams call : callParamsQueue) {
            executeCall(call);
        }
        for (Carriage car : carriages) {
            car.step();
        }
    }

    private void executeCall(CallParams callParams) {
        for (Carriage car : carriages) {
            if (car.call(callParams.getFloor(), callParams.getDirection())) {
                callParamsQueue.remove();
                return;
            }
        }
    }
}
