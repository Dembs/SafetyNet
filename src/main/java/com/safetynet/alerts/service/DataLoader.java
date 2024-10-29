package com.safetynet.alerts.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.model.Data;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;

@Service
public class DataLoader {

    private final ObjectMapper mapper = new ObjectMapper();
    private Data data ;

    @PostConstruct
    public void loadData() {
        try {
            this.data = mapper.readValue(new File("src/main/resources/data.json"), Data.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Data getData() {
        return data;
    }
}
