package com.michal.kuc.liftctl.services;

import com.michal.kuc.liftctl.model.CarriageStatus;
import com.michal.kuc.liftctl.repositories.CarriageStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class CarriageStatusServiceImpl implements CarriageStatusService {
    @Autowired
    CarriageStatusRepository carriageStatusRepository;

    @Autowired
    MongoOperations mongoOps;
    @Override
    public CarriageStatus addCarriage(CarriageStatus carriageStatus) {
        return carriageStatusRepository.save(carriageStatus);
    }

    @Override
    public List<CarriageStatus> getAllCarriages() {
        return carriageStatusRepository.findAll();
    }

    @Override
    public Optional<CarriageStatus> getCarriageById(BigInteger id) {
        return carriageStatusRepository.findById(id);
    }

    @Override
    public CarriageStatus updateCarriageById(BigInteger id, CarriageStatus carriageStatus) {
        Query query = new Query().addCriteria(Criteria.where("_id").is(id));
        Update updateDefinition = new Update()
                .set("currentFloor", carriageStatus.getCurrentFloor())
                .set("targetFloor", carriageStatus.getTargetFloor());
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);

        return mongoOps.findAndModify(query, updateDefinition, options, CarriageStatus.class);
    }

    @Override
    public void removeCarriageById(BigInteger id) {
        carriageStatusRepository.deleteById(id);
    }

    @Override
    public void removeAllCarriages() {
        carriageStatusRepository.deleteAll();
    }
}
