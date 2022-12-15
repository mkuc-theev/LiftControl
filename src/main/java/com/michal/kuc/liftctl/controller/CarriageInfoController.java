package com.michal.kuc.liftctl.controller;

import com.michal.kuc.liftctl.model.CarriageInfo;
import com.michal.kuc.liftctl.service.CarriageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping(value = "/carstat")
public class CarriageInfoController {

    @Autowired
    private CarriageInfoService carriageInfoService;

    @PostMapping
    public ResponseEntity<?> addCarriage(@RequestBody CarriageInfo carriageInfo) {
        BigInteger id = carriageInfoService.addCarriage(carriageInfo).getId();
        return new ResponseEntity<>("New lift carriage with id " + id + " created.", HttpStatus.CREATED);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllCarriages() {
        return new ResponseEntity<>(
                carriageInfoService.getAllCarriages(),
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<?> getCarriageById(@RequestParam(name = "id") BigInteger id) {
        return new ResponseEntity<>(
                carriageInfoService.getCarriageById(id).get(),
                HttpStatus.OK
        );
    }

    @PatchMapping
    public ResponseEntity<?> updateCarriageById(@RequestParam(name = "id") BigInteger id, @RequestBody CarriageInfo carriageInfo) {
        carriageInfoService.updateCarriageById(id, carriageInfo);
        return new ResponseEntity<>("Lift carriage with id " + id + " updated.", HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> removeCarriageById(@RequestParam(name = "id") BigInteger id) {
        carriageInfoService.removeCarriageById(id);
        return new ResponseEntity<>("Lift carriage with id " + id + " removed.", HttpStatus.OK);
    }

    @DeleteMapping(value = "/purge")
    public ResponseEntity<?> removeAllCarriages() {
        carriageInfoService.removeAllCarriages();
        return new ResponseEntity<>("All lift carriage records removed.", HttpStatus.OK);
    }
}
