package com.michal.kuc.liftctl.controllers;

import com.michal.kuc.liftctl.model.CarriageStatus;
import com.michal.kuc.liftctl.repositories.CarriageStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/carstat")
public class CarriageStatusController {

    @Autowired
    private CarriageStatusRepository carriageStatusRepository;

    @PostMapping
    public ResponseEntity<?> addCarriage(@RequestBody CarriageStatus carriageStatus) {
        Integer id = carriageStatusRepository.save(carriageStatus).getId();
        return new ResponseEntity<>("New lift carriage with id " + id + " created.", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllCarriages() {
        return new ResponseEntity<>(
                carriageStatusRepository.findAll(),
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<?> getCarriageById(@RequestParam(name = "id") Integer id) {
        return new ResponseEntity<>(
                carriageStatusRepository.findById(id).get(),
                HttpStatus.OK
        );
    }

    @PatchMapping
    public ResponseEntity<?> updateCarriageById(@RequestParam(name = "id") Integer id) {
        carriageStatusRepository.save(
                carriageStatusRepository.findById(id).get()
        );
        return new ResponseEntity<>("Lift carriage with id " + id + " updated.", HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> removeCarriageById(@RequestParam(name = "id") Integer id) {
        carriageStatusRepository.deleteById(id);
        return new ResponseEntity<>("Lift carriage with id " + id + " removed.", HttpStatus.OK);
    }

    @DeleteMapping(value = "/purge")
    public ResponseEntity<?> removeAllCarriages() {
        carriageStatusRepository.deleteAll();
        return new ResponseEntity<>("All lift carriage records removed.", HttpStatus.OK);
    }
}
