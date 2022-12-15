package com.michal.kuc.liftctl.service;

import com.michal.kuc.liftctl.model.CallParams;
import com.michal.kuc.liftctl.model.Carriage;
import com.michal.kuc.liftctl.model.CarriageInfo;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

@Service
public class CarriageServiceImpl implements CarriageService {
    private final List<Carriage> carriages = new ArrayList<>();
    private final Queue<CallParams> callParamsQueue = new LinkedList<>();

    @Override
    public Carriage addCarriage(CarriageInfo carriageInfo) {
        Carriage carriage = new Carriage(carriageInfo.getId(), carriageInfo.getName());
        carriages.add(carriage);
        return carriage;
    }

    @Override
    public void removeCarriage(BigInteger id) {
        carriages.removeIf(carriage -> Objects.equals(id, carriage.getId()));
    }

    @Override
    public void step() {
        for (CallParams call : callParamsQueue) {
            executeCall(call);
        }
        for (Carriage car : carriages) {
            step();
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
