import {Component, OnInit} from '@angular/core';
import {Flight} from "./flight.model";
import {DataService} from "../../assets/data-service";
import {FormControl, FormGroup, NgForm, Validators} from "@angular/forms";

@Component({
  selector: 'app-flights',
  templateUrl: './flights.component.html',
  styleUrls: ['./flights.component.scss'],
  providers:[DataService]
})


export class FlightsComponent implements OnInit{
  isOverlayShow:boolean = false;
  isEditMode!:boolean;
  public  flights !: Flight[];
  overlayForm!:FormGroup;

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

    this.overlayForm = new FormGroup({
      'oId':new FormControl(null),
      'oFlightNumber':new FormControl(null,Validators.required),
      'oArrival': new FormControl(null,Validators.required),
      'oDeparture': new FormControl(null,Validators.required),
      'oArrivalDate': new FormControl(null,Validators.required),
      'oArrivalTime': new FormControl(null, Validators.required),
      'oDepartureDate': new FormControl(null, Validators.required),
      'oDepartureTime': new FormControl(null, Validators.required)
    });
  }

  deleteFlight(flight_id : string ){
    this.dataService.removeFlight(flight_id);
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


  editFlight(flight : Flight){
    this.isEditMode = true;
    this.overlayForm.setValue({
      'oId': flight.id,
      'oFlightNumber': flight.flight_number,
      'oArrival': flight.arrival,
      'oDeparture': flight.departure,
      'oArrivalDate':flight.arrival_date,
      'oArrivalTime': flight.arrival_time,
      'oDepartureDate':flight.departure_date,
      'oDepartureTime': flight.departure_time

    })

    this.isOverlayShow = !this.isOverlayShow;
  }

  onClickAddFlight(){
    this.isEditMode = false;
    this.isOverlayShow = !this.isOverlayShow;
    this.overlayForm.reset();
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
    this.isOverlayShow = !this.isOverlayShow;
    this.overlayForm.reset();

  }

  onAddFlight(){
    const value = this.overlayForm.value;
    if (this.isEditMode){
      this.dataService.updateFlight(value);

    }
    else {
      this.dataService.addFlight(value);
    }

    this.isOverlayShow = !this.isOverlayShow;
    this.isEditMode =false;
    this.overlayForm.reset();
  }




}
