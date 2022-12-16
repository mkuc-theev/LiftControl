package com.michal.kuc.liftctl.service;

import com.michal.kuc.liftctl.model.CallParams;
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

    public void initialize() {
        System.out.println("Initializing...");
        carriageService.initialize(repositoryService.getAllCarriages());
    }

    public void step() {
        carriageService.step();
    }

    public void call(CallParams callParams) {
        carriageService.call(callParams);
    }

    public List<Carriage> getAllCarriages() {
        return carriageService.getAllCarriages();
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
