package com.michal.kuc.liftctl.service;

import com.michal.kuc.liftctl.model.Carriage;
import com.michal.kuc.liftctl.model.CarriageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class MiddlemanService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private CarriageService carriageService;


    public ResponseEntity<?> addCarriage(CarriageInfo carriageInfo) {
        BigInteger id = carriageService.addCarriage(
                repositoryService.addCarriage(carriageInfo)).getId();
        return new ResponseEntity<>("New lift carriage with id " + id + " created.", HttpStatus.CREATED);
    }

    public List<Carriage> getAllCarriages() {
        return carriageService.getAllCarriages();
    }

    public Carriage getCarriageById(BigInteger id) {
        return carriageService.getCarriageById(id).get();
    }

    public Carriage updateCarriageById(BigInteger id, CarriageInfo carriageInfo) {
        repositoryService.updateCarriageById(id, carriageInfo);
        return carriageService.updateCarriageById(carriageInfo, id).get();
    }

    public void removeCarriageById(BigInteger id) {
        repositoryService.removeCarriageById(id);
        carriageService.removeCarriage(id);
    }

    public void removeAllCarriages() {
        repositoryService.removeAllCarriages();
        carriageService.removeAllCarriages();
    }
}
