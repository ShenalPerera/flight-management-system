import {Flight} from "../flight.model";
import {Inject, Injectable} from "@angular/core";
import genUniqueId from "../../../utills/unique-id-generator";
import {Subject} from "rxjs";
import {FlightsService} from "./flights.service";

@Injectable({
  providedIn: 'root'
})

export class FlightDataService {
  flightListChanged = new Subject<Flight[]>();

  flights: Flight[] = [];

  constructor(@Inject(FlightsService) private flightService: FlightsService) {}
  getFlights() {
    return this.flights.slice();
  }

  fetchFlights() {
    this.flightService.fetchFlights().subscribe( data => {
      this.flights =data;
      this.flightListChanged.next(this.flights.slice());
    });
  }


  addFlight(value: { oId: string, oFlightNumber: string, oArrival: string, oDeparture: string, oArrivalDateNTime: string, oDepartureDateNTime: string }) {
    value.oId = genUniqueId();
    return this.flightService.addNewFlight(this.createFlight(value));
  }


  removeFlight(flight_id: string) {
    return this.flightService.removeFlight(flight_id);
  }

  updateFlight(value: {
    oId: string, oFlightNumber: string, oArrival: string, oDeparture: string, oArrivalDateNTime: string, oDepartureDateNTime: string}){
    return this.flightService.editFlight(this.createFlight(value));
  }

  getDateNTime(s: string): { date: string, time: string } {
    let result = s.split("T");
    return {date: result[0], time: result[1]};
  }

  checkEntryValid(oId: string, flight_number: string, departure_date: string): boolean {

    for (let flight of this.flights) {
      let departureDateNTime = new Date(flight.departure_date + "T" + flight.departure_time);
      let departureDateNTimeArg = new Date(departure_date);

      if (flight.flight_number === flight_number && flight.id !== oId && departureDateNTimeArg.getTime() === departureDateNTime.getTime()) {
        return false;
      }
    }
    return true;
  }

  createFlight(value: {
    oId: string, oFlightNumber: string, oArrival: string, oDeparture: string, oArrivalDateNTime: string,
    oDepartureDateNTime: string
  }):Flight{

    let arrivalDateNTime = this.getDateNTime(value.oArrivalDateNTime);
    let departureDateNTime = this.getDateNTime(value.oDepartureDateNTime);

    return new Flight(
      value.oId,
      value.oFlightNumber,
      value.oArrival,
      value.oDeparture,
      arrivalDateNTime.date,
      arrivalDateNTime.time,
      departureDateNTime.date,
      departureDateNTime.time);
  }


}