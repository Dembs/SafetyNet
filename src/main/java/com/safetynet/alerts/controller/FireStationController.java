package com.safetynet.alerts.controller;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.repository.PersonRepository;
import com.safetynet.alerts.service.DataLoader;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/firestation")
public class FireStationController {

    private final FireStationRepository fireStationRepository;

    public FireStationController(FireStationRepository fireStationRepository) {
        this.fireStationRepository = fireStationRepository;
    }

    // Endpoint to get all fireStations
    @GetMapping
    public List<FireStation> getAllFireStations() {
        return fireStationRepository.findAll();
    }

    //Endpoint to add one fireStation
    @PostMapping
    public String addOneFireStation(@RequestBody FireStation fireStation){
        fireStationRepository.save(fireStation);
        return "Fire Station added successfully!";
    }

    //Endpoint to delete one fireStation
    @DeleteMapping
    public String deleteOneFireSation(@RequestBody FireStation fireStation){
        fireStationRepository.delete(fireStation);
        return "Fire Station deleted successfully";
    }

    //Endpoint to update one fireStation
    @PutMapping
    public String updateOnePerson(@RequestBody FireStation fireStation){
        fireStationRepository.update(fireStation);
        return  "Fire Station updated successfully";
    }
}
