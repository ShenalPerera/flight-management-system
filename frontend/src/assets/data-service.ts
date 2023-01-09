import {Flight} from "../app/flights/flight.model";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn:'root'
})

export class DataService{
  flights : Flight[] = [
    new Flight(1,1,"colombo","India","2023-01-04","2023-01-05", "13:04","11:00"),

    new Flight(2,2,"Singapore","Sri Lanka","2023-01-01","2023-01-08","09:11","10:23"),
    new Flight(3,3,"Dubai","India","2023-02-03","2023-01-01","12:14","12:30"),
    new Flight(4,3,"Dubaiwe","sfIndia","2023-01-01","2023-01-01","08:00","08:30"),


  ];

  getFlights(){
    return this.flights;
  }

  addFlight(new_flight : Flight){
    this.flights.push(new_flight);
  }

  removeFlight(flight_id : number){
    const flightIndex = this.flights.findIndex((flightElement :Flight) => {
      return flightElement.id === flight_id;
    });

    if (flightIndex !== -1) {
      this.flights.splice(flightIndex, 1);
    }
  }

  updateFlight(flight:Flight){
    const flightIndex = this.flights.findIndex((flightElement :Flight) => {
      return flightElement.flight_number === flight.flight_number &&
        flightElement.departure === flight.departure &&
        flightElement.arrival === flight.arrival &&
        flightElement.arrival_date === flight.arrival_date &&
        flightElement.departure_date === flight.departure_date
    });


  }

}
