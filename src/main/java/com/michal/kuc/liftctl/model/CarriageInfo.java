package com.michal.kuc.liftctl.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;

@Document(collection = "lift-statuses")
public class CarriageInfo {
    @Id
    private BigInteger id;

    private String name;

    public CarriageInfo() {}

    public CarriageInfo(BigInteger id, String name) {
        this.id = id;
        this.name = name;
    }

    public BigInteger getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
