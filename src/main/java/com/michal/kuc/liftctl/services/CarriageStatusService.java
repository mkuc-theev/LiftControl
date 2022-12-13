package com.michal.kuc.liftctl.services;

import com.michal.kuc.liftctl.model.CarriageStatus;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface CarriageStatusService {

    CarriageStatus addCarriage(CarriageStatus carriageStatus);
    List<CarriageStatus> getAllCarriages();
    Optional<CarriageStatus> getCarriageById(BigInteger id);
    CarriageStatus updateCarriageById(BigInteger id, CarriageStatus carriageStatus);
    void removeCarriageById(BigInteger id);
    void removeAllCarriages();

}
