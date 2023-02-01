package com.fms.availabilitySearch.DAOs;

import com.fms.availabilitySearch.models.AvailableSearch;

import java.util.Date;
import java.util.List;

public interface AvailabilitySearchDao {

    public List<AvailableSearch> getAvailableSearchesByDeparture(String departure);
    public List<AvailableSearch> getAvailableSearchesByDestination(String destination);
    public List<AvailableSearch> getAvailableSearchesByLocations(String departure, String destination);
    public List<AvailableSearch> getAvailableSearchesByStartDate(Date startDate);
    public List<AvailableSearch> getAvailableSearchesByEndDate(Date endDate);
    public List<AvailableSearch> getAvailableSearchesByDates(Date startDate, Date endDate);

}
