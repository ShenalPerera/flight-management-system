import {Component, OnInit} from '@angular/core';
import {AvailableFlightModel} from "./models/available-flight.model";
import {AvailableFlightsService} from "./services/available-flights.service";
import {AirportsHandleService} from "../services/airports-handle.service";
import {NgForm} from "@angular/forms";
import {switchMap} from "rxjs";

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

  onClickSearch(filterForm: NgForm){
    console.log(filterForm.value.departureStartDate.toLocaleDateString());
  }

  private retrieveFLightsAndAirports(){
    this.availableFlightService.getAvailableFlights(this.availableFlightSearchObject).pipe(
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
}
