import {Component, OnInit} from '@angular/core';
import {AvailableFlightModel} from "./models/available-flight.model";
import {AvailableFlightsService} from "./services/available-flights.service";
import {AirportsHandleService} from "../services/airports-handle.service";
import {NgForm} from "@angular/forms";
import {filter, switchMap} from "rxjs";

@Component({
  selector: 'app-available-flights-screen',
  templateUrl: './available-flights-screen.component.html',
  styleUrls: ['./available-flights-screen.component.scss']
})
export class AvailableFlightsScreenComponent implements OnInit{
  availableFlightList !: AvailableFlightModel[];

  airports !: string[];

  availableFlightSearchObject = {flightNumber:"",departure:"",arrival:"",departureStartDate:"",departureEndDate:""};
  constructor(private availableFlightService:AvailableFlightsService, private airportHandleService:AirportsHandleService) {
  }
  ngOnInit() {
    this.retrieveFLightsAndAirports();
  }

  onClickSearch(searchCriteria:{flightNumber:string,departure:string,arrival:string,departureStartDate:Date,departureEndDate:Date}){
    let data = {
        flightNumber: searchCriteria.flightNumber,
        departure: searchCriteria.departure,
        arrival: searchCriteria.arrival,
        departureStartDate: this.parseDateObject(searchCriteria.departureStartDate),
        departureEndDate: this.parseDateObject(searchCriteria.departureEndDate)
    };

    console.log(data);
    this.getAvailableFlights(data);
  }

  onClearSearch(form:NgForm){
    form.reset(this.availableFlightSearchObject);
    this.getAvailableFlights(this.availableFlightSearchObject);
  }

  private retrieveFLightsAndAirports(){
    this.getAvailableFlights$(this.availableFlightSearchObject).pipe(
      switchMap(flightList =>{
        this.availableFlightList = flightList;
        return this.getAirports$();
    }))
      .subscribe( {
      next:(airportsList =>{
        this.airports = airportsList;
      }),
      error:(err=>{
        alert("Unexpected error occurred!");
      })
    })
  }

  private getAirports$(){
    return this.airportHandleService.getAirportsList();
  }


  private getAvailableFlights$(searchCriteria:{flightNumber:string,departure:string,arrival:string,departureStartDate:string,departureEndDate:string}){
    return this.availableFlightService.getAvailableFlights(searchCriteria);
  }

  private getAvailableFlights(searchCriteria:{flightNumber:string,departure:string,arrival:string,departureStartDate:string,departureEndDate:string}){
    this.availableFlightService.getAvailableFlights(searchCriteria).subscribe({
      next:(filteredData=>{
        this.availableFlightList = filteredData;
      }),
      error: (()=>{alert("Unexpected error occurred")})
    })
  }
  private parseDateObject(givenDateObj:Date){
    if (givenDateObj){
      return this.changeDateFormat(givenDateObj);
    }
    return ""
  }

  private changeDateFormat(dateObj:Date){
    let year = dateObj.getFullYear();
    let month = dateObj.getMonth() + 1;
    let date = dateObj.getDate();

    return year +"-" + month + "-" + date;
  }
}
