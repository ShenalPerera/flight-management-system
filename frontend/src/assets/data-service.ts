import {Flight} from "../app/flights/flight.model";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn:'root'
})

export class DataService{
  flights : Flight[] = [
    new Flight(1,"colombo","India",new Date(), new Date()),

    new Flight(2,"Singapore","Sri Lanka",new Date(), new Date()),
    new Flight(3,"Dubai","India",new Date(), new Date()),
    new Flight(4,"Iran","India",new Date(), new Date())

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
