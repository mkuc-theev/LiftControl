package com.michal.kuc.liftctl.controllers;

import com.michal.kuc.liftctl.model.CarriageStatus;
import com.michal.kuc.liftctl.services.CarriageStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping(value = "/carstat")
public class CarriageStatusController {

    @Autowired
    private CarriageStatusService carriageStatusService;

    @PostMapping
    public ResponseEntity<?> addCarriage(@RequestBody CarriageStatus carriageStatus) {
        BigInteger id = carriageStatusService.addCarriage(carriageStatus).getId();
        return new ResponseEntity<>("New lift carriage with id " + id + " created.", HttpStatus.CREATED);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllCarriages() {
        return new ResponseEntity<>(
                carriageStatusService.getAllCarriages(),
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<?> getCarriageById(@RequestParam(name = "id") BigInteger id) {
        return new ResponseEntity<>(
                carriageStatusService.getCarriageById(id).get(),
                HttpStatus.OK
        );
    }

    @PatchMapping
    public ResponseEntity<?> updateCarriageById(@RequestParam(name = "id") BigInteger id, @RequestBody CarriageStatus carriageStatus) {
        carriageStatusService.updateCarriageById(id, carriageStatus);
        return new ResponseEntity<>("Lift carriage with id " + id + " updated.", HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> removeCarriageById(@RequestParam(name = "id") BigInteger id) {
        carriageStatusService.removeCarriageById(id);
        return new ResponseEntity<>("Lift carriage with id " + id + " removed.", HttpStatus.OK);
    }

    @DeleteMapping(value = "/purge")
    public ResponseEntity<?> removeAllCarriages() {
        carriageStatusService.removeAllCarriages();
        return new ResponseEntity<>("All lift carriage records removed.", HttpStatus.OK);
    }
}
