package com.michal.kuc.liftctl.service;

import com.michal.kuc.liftctl.model.CarriageInfo;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface CarriageInfoService {

    CarriageInfo addCarriage(CarriageInfo carriageInfo);
    List<CarriageInfo> getAllCarriages();
    Optional<CarriageInfo> getCarriageById(BigInteger id);
    CarriageInfo updateCarriageById(BigInteger id, CarriageInfo carriageInfo);
    void removeCarriageById(BigInteger id);
    void removeAllCarriages();

}
