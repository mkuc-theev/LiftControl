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
/**
 * Implementation of the carriage info service
 */
@Service
public class RepositoryService {
    @Autowired
    CarriageInfoRepository carriageInfoRepository;
    @Autowired
    MongoOperations mongoOps;


    public CarriageInfo addCarriage(CarriageInfo carriageInfo) {
        return carriageInfoRepository.save(carriageInfo);
    }


    public List<CarriageInfo> getAllCarriages() {
        return carriageInfoRepository.findAll();
    }


    public CarriageInfo updateCarriageById(BigInteger id, CarriageInfo carriageInfo) {
        Query query = new Query().addCriteria(Criteria.where("_id").is(id));
        Update updateDefinition = new Update()
                .set("name", carriageInfo.getName());
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);

        return mongoOps.findAndModify(query, updateDefinition, options, CarriageInfo.class);
    }

    public void removeCarriageById(BigInteger id) {
        carriageInfoRepository.deleteById(id);
    }


    public void removeAllCarriages() {
        carriageInfoRepository.deleteAll();
    }
}
