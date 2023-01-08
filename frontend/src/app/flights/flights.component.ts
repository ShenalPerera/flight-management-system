import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Flight} from "./flight.model";
import {DataService} from "../../assets/data-service";
import {MatDatepickerInputEvent} from "@angular/material/datepicker";

@Component({
  selector: 'app-flights',
  templateUrl: './flights.component.html',
  styleUrls: ['./flights.component.scss'],
  providers:[DataService]
})


export class FlightsComponent implements OnInit{

  public  flights !: Flight[];

  public searchOptions = {
      flight_number : ' ',
      arrival : ' ',
      departure: ' ',
      arrivalDate: ' ',
      departureDate: ' '
  };


  constructor(private dataService : DataService) {

  }

  ngOnInit() {
    this.flights = this.dataService.getFlights();


  }

  deleteFlight(flight : Flight){
    this.dataService.removeFlight(flight);
    this.searchOptions = {
      flight_number: ' ',
      arrival:  ' ',
      departure: ' ',
      arrivalDate: ' ',
      departureDate: ' '
    }
  }

  onChange(item : any, prop: string){
    if (prop === 'flight_number'){
      this.searchOptions.flight_number = item;
    }
    if (prop === 'arrival'){
      this.searchOptions.arrival = item;
    }
    if (prop === 'departure'){
      this.searchOptions.departure = item;
    }
    // if (prop === 'arrival_date'){
    //   this.searchOptions.arrivalDate = item
    // }
    // if (prop === 'departure_date'){
    //   this.searchOptions.departureDate = item;
    // }
  }

}
