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
 * Service that interfaces with the database, allowing for the persistence of carriages between sessions,
 * prevents losing IDs and names.
 */
@Service
public class RepositoryService {
    @Autowired
    CarriageInfoRepository carriageInfoRepository;
    @Autowired
    MongoOperations mongoOps;


    /**
     * Adds a new carriage to the database
     *
     * @param carriageInfo Information about the carriage to be added
     * @return The entity passed to the method
     */
    public CarriageInfo addCarriage(CarriageInfo carriageInfo) {
        return carriageInfoRepository.save(carriageInfo);
    }


    /**
     * Fetches all stored carriage info from the database
     *
     * @return A list of CarriageInfo objects
     */
    public List<CarriageInfo> getAllCarriages() {
        return carriageInfoRepository.findAll();
    }


    /**
     * Renames specified carriage in the database
     *
     * @param id           ID of carriage to be renamed
     * @param carriageInfo Information object containing the new name
     */
    public void updateCarriageById(BigInteger id, CarriageInfo carriageInfo) {
        Query query = new Query().addCriteria(Criteria.where("_id").is(id));
        Update updateDefinition = new Update()
                .set("name", carriageInfo.getName());
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);

        mongoOps.findAndModify(query, updateDefinition, options, CarriageInfo.class);
    }

    /**
     * Removes specified carriage from the database
     *
     * @param id ID of carriage to be removed
     */
    public void removeCarriageById(BigInteger id) {
        carriageInfoRepository.deleteById(id);
    }


    /**
     * Removes all currently stored carriages from the database
     */
    public void removeAllCarriages() {
        carriageInfoRepository.deleteAll();
    }
}
