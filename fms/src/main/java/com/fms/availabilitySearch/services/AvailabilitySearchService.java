package com.fms.availabilitySearch.services;

import com.fms.availabilitySearch.DAOs.AvailabilitySearchDao;
import com.fms.availabilitySearch.models.AvailableFlight;
import com.fms.availabilitySearch.models.AvailableSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailabilitySearchService {
    private final AvailabilitySearchDao availabilitySearchDao;

    @Autowired
    public AvailabilitySearchService(AvailabilitySearchDao availabilitySearchDao) {
        this.availabilitySearchDao = availabilitySearchDao;
    }

    public List<AvailableFlight> getAllAvailableFlights(AvailableSearch availableSearch) {
        return availabilitySearchDao.getAvailableFlights(availableSearch);
    }
}
