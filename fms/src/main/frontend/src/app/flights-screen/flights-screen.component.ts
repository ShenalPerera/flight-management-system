import {Component, OnDestroy, OnInit} from '@angular/core';
import {Flight} from "./flight.model";
import {DataService} from "../../assets/data-service";
import {AbstractControl, FormControl, FormGroup, NgForm, Validators} from "@angular/forms";
import {arrivalDatesValidator, arrivalDepartureValidator} from "../../utills/validator-functions";
import {Observable, Subscription} from "rxjs";

@Component({
  selector: 'app-flights-screen',
  templateUrl: './flights-screen.component.html',
  styleUrls: ['./flights-screen.component.scss']
})


export class FlightsScreenComponent implements OnInit ,OnDestroy{
  isOverlayShow: boolean = false;
  isEditMode!: boolean;
  public flights !: Flight[];
  overlayForm!: FormGroup;
  public searchOptions = {
    flight_number: '',
    arrival: '',
    departure: '',
    arrivalDate: '',
    departureDate: '',
    arrivalTime: '',
    departureTime: ''
  };

  public formTempData!: {};

  private flightArraySubscription ?: Subscription;
  private errorResponseSubscription?:Subscription;

  constructor(private dataService: DataService) {

  }

  ngOnInit() {
    this.dataService.fetchFlights();
    this.flightArraySubscription = this.dataService.flightListChanged.subscribe((updatedFlightList:Flight[]) => {
      this.flights = updatedFlightList;
    })

    this.overlayForm = new FormGroup({
      'oId': new FormControl(null),
      'oFlightNumber': new FormControl(null, Validators.required),
      'oArrival': new FormControl(null, Validators.required),
      'oDeparture': new FormControl(null, Validators.required),
      'oArrivalDateNTime': new FormControl(null, Validators.required),
      'oDepartureDateNTime': new FormControl(null, Validators.required)

    }, {
      validators: [arrivalDatesValidator, arrivalDepartureValidator],
      updateOn:"blur"
    });

  }

  ngOnDestroy() {
    this.flightArraySubscription?.unsubscribe();
  }

  onDeleteFlight(flight_id: string, searchForm: NgForm) {
    this.dataService.removeFlight(flight_id).subscribe( {
      next:(response)=>{
        this.dataService.fetchFlights();
        searchForm.reset();
      },
      error:(err)=>console.log("Tis is an error" + err.body)});


  }


  onEditFlight(flight: Flight) {
    this.isEditMode = true;
    this.formTempData = {
      'oId': flight.id,
      'oFlightNumber': flight.flight_number,
      'oArrival': flight.arrival,
      'oDeparture': flight.departure,
      'oArrivalDateNTime': flight.arrival_date + "T" + flight.arrival_time,
      'oDepartureDateNTime': flight.departure_date + "T" + flight.departure_time,
    }
    this.overlayForm.setValue(this.formTempData);
    this.isOverlayShow = !this.isOverlayShow;
  }


  onClickAddFlight() {
    this.isEditMode = false;
    this.isOverlayShow = !this.isOverlayShow;
    this.overlayForm.reset();
  }

  onClearSearch(f: NgForm) {
    f.reset();
  }

  onCancelEdit() {
    if (confirm("Are you want to exit from editing?")) {

      this.isOverlayShow = !this.isOverlayShow;
      this.overlayForm.reset();
    }

  }

  onClickReset() {
    if (this.isEditMode) {
      this.overlayForm.setValue(this.formTempData);
    } else {
      this.overlayForm.reset();
    }
    this.overlayForm.markAsPristine();
  }

  onSubmitForm() {
    const value = this.overlayForm.value;
    let tempSubscription!:Observable<Object>;

    if (this.isEditMode) {
      tempSubscription =  this.dataService.updateFlight(value);
    }
    else {
      tempSubscription =  this.dataService.addFlight(value);
    }

    tempSubscription.subscribe({
      next:()=>{
        this.resetFormScreeMode();
        this.dataService.fetchFlights();
      },
      error:err => {
        alert(err.error);
      }
    })
  }

  resetFormScreeMode(){
    this.isOverlayShow = !this.isOverlayShow;
    this.isEditMode = false;
    this.overlayForm.reset();
  }

  invalidFlightEntry(control: AbstractControl): Promise<any> | Observable<any> {
    return new Promise((resolve, reject) => {
      const oId = control.get('oId')?.value;
      const departureDateNTime = control.get('oDepartureDateNTime')?.value;
      const flightNumber = control.get('oFlightNumber')?.value;

      if (!this.dataService.checkEntryValid(oId, flightNumber, departureDateNTime)) {
        resolve({'duplicateEntry': true});
      }
      resolve(null);
    });
  }

}
