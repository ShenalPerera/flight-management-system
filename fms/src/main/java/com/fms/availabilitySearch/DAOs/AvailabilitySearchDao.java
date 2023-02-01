package com.fms.availabilitySearch.DAOs;

import com.fms.availabilitySearch.models.AvailableSearch;

import java.util.Date;
import java.util.List;

public interface AvailabilitySearchDao {

    public List<AvailableSearch> getAvailableFlights(AvailableSearch availableSearch);

}
