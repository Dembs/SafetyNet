package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.DataLoader;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FireStationRepositoryImpl implements FireStationRepository {
    private List<FireStation> fireStations ;

    public FireStationRepositoryImpl(DataLoader dataLoader){
        this.fireStations = new ArrayList<>(dataLoader.getData().getFirestations());
    }

    @Override
    public List<FireStation>findAll(){
        return new ArrayList<>(fireStations);
    }

    @Override
    public Optional<FireStation> findByAddress(String address){
        return fireStations.stream()
                           .filter(fireStation -> fireStation.getAddress()
                                                             .equals(address))
                           .findFirst();
    }

    @Override
    public void save(FireStation fireStation){
        fireStations.add(fireStation);
    }

    @Override
    public void delete(FireStation fireStation){
        fireStations.remove(fireStation);
    }

    @Override
    public void update(FireStation updatedFireStation){
        findByAddress(updatedFireStation.getAddress())
                .ifPresent(stationToUpdate -> {
                    stationToUpdate.setStation(updatedFireStation.getStation());
                } );
    }

    @Override
    public Optional<List<String>> findAddressByStationNumber(String stationNumber) {
        List<String> addresses = fireStations.stream()
                                             .filter(fireStation -> fireStation.getStation().equals(stationNumber))
                                             .map(FireStation::getAddress)
                                             .toList();
        return Optional.ofNullable(addresses.isEmpty() ? null : addresses);
    }
}
