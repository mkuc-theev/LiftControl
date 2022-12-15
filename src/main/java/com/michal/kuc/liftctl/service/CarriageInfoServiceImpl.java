package com.michal.kuc.liftctl.service;

import com.michal.kuc.liftctl.model.CarriageInfo;
import com.michal.kuc.liftctl.repository.CarriageInfoRepository;
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
public class CarriageInfoServiceImpl implements CarriageInfoService {
    @Autowired
    CarriageInfoRepository carriageInfoRepository;
    @Autowired
    MongoOperations mongoOps;

    @Override
    public CarriageInfo addCarriage(CarriageInfo carriageInfo) {
        return carriageInfoRepository.save(carriageInfo);
    }

    @Override
    public List<CarriageInfo> getAllCarriages() {
        return carriageInfoRepository.findAll();
    }

    @Override
    public Optional<CarriageInfo> getCarriageById(BigInteger id) {
        return carriageInfoRepository.findById(id);
    }

    @Override
    public CarriageInfo updateCarriageById(BigInteger id, CarriageInfo carriageInfo) {
        Query query = new Query().addCriteria(Criteria.where("_id").is(id));
        Update updateDefinition = new Update()
                .set("name", carriageInfo.getName());
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);

        return mongoOps.findAndModify(query, updateDefinition, options, CarriageInfo.class);
    }

    @Override
    public void removeCarriageById(BigInteger id) {
        carriageInfoRepository.deleteById(id);
    }

    @Override
    public void removeAllCarriages() {
        carriageInfoRepository.deleteAll();
    }
}
