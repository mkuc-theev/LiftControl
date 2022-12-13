package com.michal.kuc.liftctl.repositories;

import com.michal.kuc.liftctl.model.CarriageStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface CarriageStatusRepository extends MongoRepository<CarriageStatus, BigInteger> {
}
