import {Component, OnInit} from '@angular/core';
import {AvailableFlightModel} from "./models/available-flight.model";
import {AvailableFlightsService} from "./services/available-flights.service";

@Component({
  selector: 'app-available-flights-screen',
  templateUrl: './available-flights-screen.component.html',
  styleUrls: ['./available-flights-screen.component.scss']
})
export class AvailableFlightsScreenComponent implements OnInit{
  availableFlightList !: AvailableFlightModel[];

  availableFlightSearchObject = {flightNumber:"",departure:"",arrival:"",departureStartDate:"",departureEndDate:""};
  constructor(private availableFlightService:AvailableFlightsService) {
  }
  ngOnInit() {
    this.availableFlightService.getAvailableFlights(this.availableFlightSearchObject).subscribe({
      next:(filteredFlights => {
        this.availableFlightList = filteredFlights;
      }),
      error:( error => {
        alert("Unexpected error occurred!");
      })
    })
  }
}
