package com.fms.services;

import com.fms.exceptions.FMSException;
import com.fms.httpsStatusCodesFMS.HttpStatusCodesFMS;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class AirportsFileHandler {
    private final String filename = "airports.txt";

    public List<String> getAirportFromFile(){
        List<String> airports = new ArrayList<>();

        try {
            Resource resource = new ClassPathResource(filename);
            InputStream airportFile = resource.getInputStream();

            Scanner fileReader = new Scanner(airportFile);

            while (fileReader.hasNextLine()) {
                String airport = fileReader.nextLine();
                airports.add(airport);
            }
            fileReader.close();
        }
        catch (FileNotFoundException e){
            throw new FMSException(HttpStatusCodesFMS.ENTRY_NOT_FOUND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return airports;
    }
}
