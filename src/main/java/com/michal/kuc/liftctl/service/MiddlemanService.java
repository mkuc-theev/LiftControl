package com.michal.kuc.liftctl.service;

import com.michal.kuc.liftctl.model.CallParams;
import com.michal.kuc.liftctl.model.Carriage;
import com.michal.kuc.liftctl.model.CarriageInfo;
import com.michal.kuc.liftctl.model.SendParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * Service that connects the web controller with the rest of the program, ensuring parity of information
 * between the database and currently managed carriages and deferring simulation control
 */
@Service
public class MiddlemanService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private CarriageService carriageService;

    @Value("${liftctl.carriages.maximum}")
    private Integer maxCarriages;


    /**
     * Checks if the maximum number of allowed carriages was reached
     *
     * @return True if the database holds the maximum number of carriages allowed, False if not
     */
    public Boolean isAtMaxCars() {
        return repositoryService.getAllCarriages().size() >= maxCarriages;
    }

    /**
     * Adds a new carriage to the database and carriage service
     *
     * @param carriageInfo Information about the carriage to be added
     */
    public void addCarriage(CarriageInfo carriageInfo) {
        BigInteger id = carriageService.addCarriage(
                repositoryService.addCarriage(carriageInfo)).getId();
    }

    /**
     * Initializes carriage service with information from the database
     */
    public void initialize() {
        carriageService.initialize(repositoryService.getAllCarriages());
    }

    /**
     * Steps the simulation forward
     */
    public void step() {
        carriageService.step();
    }

    /**
     * Adds a call to the queue
     *
     * @param callParams Information about the floor at which the call was made and intended direction
     */
    public void call(CallParams callParams) {
        carriageService.call(callParams);
    }

    /**
     * Sends specified carriage to the given floor
     *
     * @param id    ID of carriage to be sent
     * @param floor Floor to send the carriage to
     */
    public void send(BigInteger id, SendParam floor) {
        carriageService.send(id, floor);
    }

    /**
     * Fetches list of carriages managed by the carriage service
     *
     * @return List of currently managed carriages
     */
    public List<Carriage> getAllCarriages() {
        return carriageService.getAllCarriages();
    }

    /**
     * Updates given carriage with a new name
     *
     * @param id           ID of carriage to be renamed
     * @param carriageInfo Information object containing the new name
     */
    public void updateCarriageById(BigInteger id, CarriageInfo carriageInfo) {
        repositoryService.updateCarriageById(id, carriageInfo);
        carriageService.updateCarriageById(carriageInfo, id).get();
    }

    /**
     * Removes specified carriage from the database and the carriage service list
     *
     * @param id ID of carriage to be removed
     */
    public void removeCarriageById(BigInteger id) {
        repositoryService.removeCarriageById(id);
        carriageService.removeCarriage(id);
    }

    /**
     * Removes all carriages from the database and the carriage service list
     */
    public void removeAllCarriages() {
        repositoryService.removeAllCarriages();
        carriageService.removeAllCarriages();
    }

    /**
     * @return The maximum number of carriages allowed by the system
     */
    public Integer getMaxCarriages() {
        return maxCarriages;
    }
}
