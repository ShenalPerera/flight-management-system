import {Flight} from "../app/flights-screen/flight.model";
import {Injectable} from "@angular/core";
import genUniqueId from "../utills/unique-id-generator";

@Injectable({
  providedIn:'root'
})

export class DataService{
  flights : Flight[] = [
    new Flight("1","1","colombo","India","2023-01-04", "13:04","2023-01-05","11:00"),

    new Flight("2","2","Singapore","Sri Lanka","2023-01-01","09:11","2023-01-08","10:23"),
    new Flight("3","1","Dubai","India","2023-02-03","12:14","2023-01-01","12:30"),
    new Flight("4","1","Dubaiwe","sfIndia","2023-01-01","08:00","2023-01-01","08:30"),

    new Flight("5","5","colombo","India","2023-01-04", "13:04","2023-01-05","11:00"),

    new Flight("6","6","Singapore","Sri Lanka","2023-01-01","09:11","2023-01-08","10:23"),
    new Flight("7","7","Dubai","India","2023-02-03","12:14","2023-01-01","12:30"),
    new Flight("8","8","Dubaiwe","sfIndia","2023-01-01","08:00","2023-01-01","08:30"),

    new Flight("9","9","colombo","India","2023-01-04", "13:04","2023-01-05","11:00"),

    new Flight("10","10","Singapore","Sri Lanka","2023-01-01","09:11","2023-01-08","10:23"),
    new Flight("11","12","Dubai","India","2023-02-03","12:14","2023-01-01","12:30"),
    new Flight("12","12","Dubaiwe","sfIndia","2023-01-01","08:00","2023-01-01","08:30"),

    new Flight("13","12","colombo","India","2023-01-04", "13:04","2023-01-05","11:00"),

    new Flight("14","14","Singapore","Sri Lanka","2023-01-01","09:11","2023-01-08","10:23"),
    new Flight("15","15","Dubai","India","2023-02-03","12:14","2023-01-01","12:30"),
    new Flight("16","16","Dubaiwe","sfIndia","2023-01-01","08:00","2023-01-01","08:30"),

    new Flight("17","1","colombo","India","2023-01-04", "13:04","2023-01-05","11:00"),

    new Flight("18","2","Singapore","Sri Lanka","2023-01-01","09:11","2023-01-08","10:23"),
    new Flight("19","3","Dubai","India","2023-02-03","12:14","2023-01-01","12:30"),
    new Flight("20","3","Dubaiwe","sfIndia","2023-01-01","08:00","2023-01-01","08:30"),

  ];

  getFlights(){
    return this.flights;
  }

  addFlight(value:{oId:string,oFlightNumber:string, oArrival:string,oDeparture:string,oArrivalDateNTime:string,oDepartureDateNTime:string}){

    let id:string = genUniqueId();
    let arrivalDateNTime = this.getDateNTime(value.oArrivalDateNTime);
    let departureDateNTime = this.getDateNTime(value.oDepartureDateNTime);
    this.flights.push(new Flight(id,
      value.oFlightNumber,
      value.oArrival,
      value.oDeparture,
      arrivalDateNTime.date,
      arrivalDateNTime.time,
      departureDateNTime.date,
      departureDateNTime.time
    ));
  }



  removeFlight(flight_id : string){
    const flightIndex = this.flights.findIndex((flightElement :Flight) => {
      return flightElement.id === flight_id;
    });

    if (flightIndex !== -1) {
      this.flights.splice(flightIndex, 1);
    }
  }

  updateFlight(value:{oId:string,oFlightNumber:string, oArrival:string,oDeparture:string,oArrivalDateNTime:string,
    oDepartureDateNTime:string}){

    const flightIndex = this.flights.findIndex((flightElement :Flight) => {
      return flightElement.id === value.oId;
    });

    let arrivalDateNTime = this.getDateNTime(value.oArrivalDateNTime);
    let departureDateNTime = this.getDateNTime(value.oDepartureDateNTime);

    this.flights[flightIndex] = new Flight(value.oId,
      value.oFlightNumber,
      value.oArrival,
      value.oDeparture,
      arrivalDateNTime.date,
      arrivalDateNTime.time,
      departureDateNTime.date,
      departureDateNTime.time);

  }

  getDateNTime(s:string):{date:string,time:string}{
    let result = s.split("T");
    return {date:result[0],time:result[1]};
  }

  checkEntryValid(oId:string, flight_number:string, departure_date:string):boolean{

    for (let flight of this.flights){
      let departureDateNTime = new Date(flight.departure_date + "T" +flight.departure_time );
      let departureDateNTimeArg = new Date(departure_date);

      if (flight.flight_number === flight_number && flight.id !== oId && departureDateNTimeArg.getTime() === departureDateNTime.getTime()){
        return false;
      }
    }
    return true;
  }


}
