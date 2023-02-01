package com.fms.fares.daos;

import com.fms.fares.models.Fare;

import java.util.List;

public interface FareDao {
    List<Fare> getSearchedFares(String departure, String arrival);
}
