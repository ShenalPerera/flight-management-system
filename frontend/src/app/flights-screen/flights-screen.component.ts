import {Component, OnInit} from '@angular/core';
import {Flight} from "./flight.model";
import {DataService} from "../../assets/data-service";
import {FormControl, FormGroup, NgForm, Validators} from "@angular/forms";
import {arrivalDatesValidator} from "../../utills/validator-functions";

@Component({
  selector: 'app-flights-screen',
  templateUrl: './flights-screen.component.html',
  styleUrls: ['./flights-screen.component.scss'],
  providers:[DataService]
})


export class FlightsScreenComponent implements OnInit{
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
      'oArrivalDateNTime': new FormControl(null,Validators.required),
      'oDepartureDateNTime': new FormControl(null, Validators.required)

    },{validators:arrivalDatesValidator});
  }

  onDeleteFlight(flight_id : string ){
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


  onEditFlight(flight : Flight){
    this.isEditMode = true;
    this.overlayForm.setValue({
      'oId': flight.id,
      'oFlightNumber': flight.flight_number,
      'oArrival': flight.arrival,
      'oDeparture': flight.departure,
      'oArrivalDateNTime':flight.arrival_date +"T" + flight.arrival_time,
      'oDepartureDateNTime':flight.departure_date + "T" + flight.departure_time,


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
    if (confirm("Are you want to exit from editing?")){
      this.isOverlayShow = !this.isOverlayShow;

      this.overlayForm.reset();
    }


  }

  onSubmitForm(){
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
