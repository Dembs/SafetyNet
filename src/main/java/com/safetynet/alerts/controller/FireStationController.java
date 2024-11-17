package com.safetynet.alerts.controller;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.service.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/firestation")
public class FireStationController {
    @Autowired
    private FireStationRepository fireStationRepository;
    @Autowired
    private FireStationService fireStationService;

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

    //Endpoint to get a list of person per station number
    @GetMapping(params = "stationNumber")
    public Map<String, Object> getPersonsByStation(@RequestParam String stationNumber) {
        return fireStationService.getPersonsByStation(stationNumber);
    }
}
