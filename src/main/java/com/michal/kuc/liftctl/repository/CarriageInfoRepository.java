package com.michal.kuc.liftctl.repository;

import com.michal.kuc.liftctl.model.CarriageInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
/**
 * Interface that allows interaction with the database
 */
@Repository
public interface CarriageInfoRepository extends MongoRepository<CarriageInfo, BigInteger> {
}
