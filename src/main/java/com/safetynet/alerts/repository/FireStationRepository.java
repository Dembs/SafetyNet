package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.FireStation;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing and querying FireStation .
 */
public interface FireStationRepository {

    /**
     * Retrieves all fire station from the repository.
     *
     * @return a list of all FireStation.
     */
    List<FireStation> findAll();

    /**
     * Finds a fire station by its associated address.
     *
     * @param address the address of the fire station.
     * @return an Optional containing the found FireStation, or empty if no match is found.
     */
    Optional<FireStation> findByAddress(String address);

    /**
     * Updates the details of an existing fire station in the repository.
     *
     * @param fireStation the FireStation with updated information.
     */
    void update(FireStation fireStation);

    /**
     * Saves a new fire station to the repository.
     *
     * @param fireStation the FireStation to be saved.
     */
    void save(FireStation fireStation);

    /**
     * Deletes a fire station from the repository.
     *
     * @param fireStation the FireStation to be deleted.
     */
    void delete(FireStation fireStation);

    /**
     * Finds all addresses covered by a specific fire station number.
     *
     * @param stationNumber the fire station number.
     * @return an Optional containing a list of addresses covered by the fire station,
     * or empty if no addresses are found.
     */
    Optional<List<String>> findAddressByStationNumber(String stationNumber);
}
