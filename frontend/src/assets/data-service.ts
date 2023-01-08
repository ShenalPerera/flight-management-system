import {Flight} from "../app/flights/flight.model";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn:'root'
})

export class DataService{
  flights : Flight[] = [
    new Flight(1,"colombo","India","1/2/2023","12/2/2023", "",""),

    new Flight(2,"Singapore","Sri Lanka","11/12/2023","12/12/2023","",""),
    new Flight(3,"Dubai","India","14/2/2023","15/2/2023","",""),
    new Flight(3,"Dubaiwe","sfIndia","24/2/2023","15/2/2023","",""),


  ];

  getFlights(){
    return this.flights;
  }

  addFlight(new_flight : Flight){
    this.flights.push(new_flight);
  }

  removeFlight(flight : Flight){
    const flightIndex = this.flights.findIndex((flightElement :Flight) => {
      return flightElement.flight_number === flight.flight_number &&
        flightElement.departure === flight.departure &&
        flightElement.arrival === flight.arrival &&
        flightElement.arrival_date === flight.arrival_date &&
        flightElement.departure_date === flight.departure_date
    });

    if (flightIndex !== -1) {
      this.flights.splice(flightIndex, 1);
    }
  }
}
