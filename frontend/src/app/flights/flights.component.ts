import {Component, OnInit} from '@angular/core';
import {Flight} from "./flight.model";
import {DataService} from "../../assets/data-service";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-flights',
  templateUrl: './flights.component.html',
  styleUrls: ['./flights.component.scss'],
  providers:[DataService]
})


export class FlightsComponent implements OnInit{
  toggle:boolean = true;
  public  flights !: Flight[];

  public searchOptions = {
      flight_number : '',
      arrival : '',
      departure: '',
      arrivalDate: '',
      departureDate: '',
      arrivalTime: '',
      departureTime: ''
  };


  constructor(private dataService : DataService) {

  }

  ngOnInit() {
    this.flights = this.dataService.getFlights();


  }

  deleteFlight(flight : Flight){
    this.dataService.removeFlight(flight);
    this.searchOptions = {
      flight_number: '',
      arrival:  '',
      departure: '',
      arrivalDate: '',
      departureDate: '',
      arrivalTime: '',
      departureTime: ''
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
    if (prop === 'arrival_date'){
      this.searchOptions.arrivalDate = item

    }
    if (prop === 'departure_date'){
      this.searchOptions.departureDate = item;
    }
    if (prop === 'arrival_time'){
      this.searchOptions.arrivalTime = item;

    }

    if (prop === 'departure_time'){
      this.searchOptions.departureTime = item;

    }
  }

  onClearSearch(f:NgForm){
    f.reset({
      fNumber:"",
      fDeparture:"",
      fArrival:"",
      fDepartureDate:"",
      fArrivalDate:"",
      fArrivalTime:"",
      fDepartureTime:""
    });

  }

  onClickBackdrop(){
    this.toggle = !this.toggle;
  }

  onAddFlight(form:NgForm){
    const value = form.value;
    this.dataService.addFlight(new Flight(
      value.oFlightNumber,
      value.oArrival,value.oDeparture,
      value.oArrivalDate,
      value.oDepartureDate,
      value.oArrivalTime,
      value.oDepartureTime))
  }

}
