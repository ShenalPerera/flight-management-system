package com.fms.availabilitySearch.DAOs;

import com.fms.availabilitySearch.models.AvailableFlight;
import com.fms.availabilitySearch.models.AvailableSearch;

import java.util.List;

public interface AvailabilitySearchDao {

    List<AvailableFlight> getAvailableFlights(AvailableSearch availableSearch);

}
