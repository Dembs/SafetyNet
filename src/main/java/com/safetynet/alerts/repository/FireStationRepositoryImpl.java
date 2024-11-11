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
    public Optional<FireStation>findByAdress(String adress){
        return fireStations.stream().filter(fireStation -> fireStation.getAddress().equals(adress)).findFirst();
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
        findByAdress(updatedFireStation.getAddress())
                .ifPresent(stationToUpdate -> {
                    stationToUpdate.setStation(updatedFireStation.getStation());
                } );
    }
}
