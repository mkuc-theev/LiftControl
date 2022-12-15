package com.michal.kuc.liftctl.service;

import com.michal.kuc.liftctl.model.Carriage;
import com.michal.kuc.liftctl.model.CarriageInfo;

import java.math.BigInteger;

public interface CarriageService {

    Carriage addCarriage(CarriageInfo carriageInfo);

    void removeCarriage(BigInteger id);
    void step();


}
