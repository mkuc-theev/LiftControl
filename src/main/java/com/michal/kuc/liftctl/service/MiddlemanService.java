package com.michal.kuc.liftctl.service;

import com.michal.kuc.liftctl.model.CallParams;
import com.michal.kuc.liftctl.model.Carriage;
import com.michal.kuc.liftctl.model.CarriageInfo;
import com.michal.kuc.liftctl.model.SendParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class MiddlemanService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private CarriageService carriageService;


    public void addCarriage(CarriageInfo carriageInfo) {
        BigInteger id = carriageService.addCarriage(
                repositoryService.addCarriage(carriageInfo)).getId();
        System.out.println("New lift carriage with id " + id + " created.");
    }

    public void initialize() {
        System.out.println("Initializing...");
        carriageService.initialize(repositoryService.getAllCarriages());
    }

    public void step() {
        System.out.println("Advancing sim a step forward...");
        carriageService.step();
    }

    public void call(CallParams callParams) {
        System.out.println("Adding call to queue with parameters: " + callParams + "...");
        carriageService.call(callParams);
    }

    public void send(BigInteger id, SendParam floor) {
        System.out.println("Sending carriage " + id + " to floor " + floor + "...");
        carriageService.send(id, floor);
    }
    public List<Carriage> getAllCarriages() {
        System.out.println("Fetching carriage list...");
        return carriageService.getAllCarriages();
    }

    public Carriage updateCarriageById(BigInteger id, CarriageInfo carriageInfo) {
        System.out.println("Updating carriage " + id + " with name " + carriageInfo.getName() + "...");
        repositoryService.updateCarriageById(id, carriageInfo);
        return carriageService.updateCarriageById(carriageInfo, id).get();
    }

    public void removeCarriageById(BigInteger id) {
        System.out.println("Removing carriage " + id + "...");
        repositoryService.removeCarriageById(id);
        carriageService.removeCarriage(id);
    }

    public void removeAllCarriages() {
        System.out.println("Removing all carriages...");
        repositoryService.removeAllCarriages();
        carriageService.removeAllCarriages();
    }
}
