import {Component, OnInit} from '@angular/core';
import {AvailableFlightModel} from "./models/available-flight.model";
import {AvailableFlightsService} from "./services/available-flights.service";
import {AirportsHandleService} from "../services/airports-handle.service";

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
    this.availableFlightService.getAvailableFlights(this.availableFlightSearchObject).subscribe({
      next:(filteredFlights => {
        this.availableFlightList = filteredFlights;
      }),
      error:( err => {
        alert("Unexpected error occurred!");
      })
    });

    this.airportHandleService.getAirportsList().subscribe( {
      next:(airportsList =>{
        this.airports = airportsList;
      }),
      error:(err=>{
        alert("Unexpected error occurred!");
      })
    })
  }
}
