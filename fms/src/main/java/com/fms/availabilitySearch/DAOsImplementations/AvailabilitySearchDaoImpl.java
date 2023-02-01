package com.fms.availabilitySearch.DAOsImplementations;

import com.fms.availabilitySearch.DAOs.AvailabilitySearchDao;
import com.fms.availabilitySearch.models.AvailableSearch;

import java.util.Date;
import java.util.List;

public class AvailabilitySearchDaoImpl implements AvailabilitySearchDao {

//    String GET_SEARCHES_BY_DEPARTURE =

    @Override
    public List<AvailableSearch> getAvailableSearchesByDeparture(String departure) {
        return null;
    }

    @Override
    public List<AvailableSearch> getAvailableSearchesByDestination(String destination) {
        return null;
    }

    @Override
    public List<AvailableSearch> getAvailableSearchesByLocations(String departure, String destination) {
        return null;
    }

    @Override
    public List<AvailableSearch> getAvailableSearchesByStartDate(Date startDate) {
        return null;
    }

    @Override
    public List<AvailableSearch> getAvailableSearchesByEndDate(Date endDate) {
        return null;
    }

    @Override
    public List<AvailableSearch> getAvailableSearchesByDates(Date startDate, Date endDate) {
        return null;
    }
}
