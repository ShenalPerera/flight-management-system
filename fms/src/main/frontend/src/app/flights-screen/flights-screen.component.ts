import {Component, OnDestroy, OnInit} from '@angular/core';
import {Flight} from "./flight.model";
import {FlightDataService} from "./flight-services/flight-data-service";
import {FormControl, FormGroup, NgForm, Validators} from "@angular/forms";
import {arrivalDatesValidator, arrivalDepartureValidator} from "../../utills/validator-functions";
import {Observable, Subscription} from "rxjs";
import {HttpResponse} from "@angular/common/http";
import {HttpStatusCodesFMS} from "../http-status-codes-fms/httpStatusCodes.enum";
import {AirportsHandleService} from "../services/airports-handle.service";

@Component({
  selector: 'app-flights-screen',
  templateUrl: './flights-screen.component.html',
  styleUrls: ['./flights-screen.component.scss']
})


export class FlightsScreenComponent implements OnInit, OnDestroy {
  isOverlayShow: boolean = false;
  isEditMode!: boolean;
  public flights !: Flight[];

  tempFlight !: Flight;

  public airports !: string[];
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

  private airportsListSubscription ?: Subscription;

  constructor(private dataService: FlightDataService, private airportsListService: AirportsHandleService) {

  }

  ngOnInit() {
    this.dataService.fetchFlights();
    this.airportsListService.getAirportsList().subscribe(response => {
      localStorage.setItem("airports", JSON.stringify(response));
      this.airports = response;
    });

    this.flightArraySubscription = this.dataService.flightListChanged.subscribe((updatedFlightList: Flight[]) => {
      this.flights = updatedFlightList;
    });

    this.airportsListSubscription = this.airportsListService.airportsListChange.subscribe((airportsList: string[]) => {
      this.airports = airportsList;
    });


    this.overlayForm = new FormGroup({
      'oId': new FormControl(null),
      'oFlightNumber': new FormControl(null, [Validators.required, Validators.pattern("^[A-Za-z]{3}[0-9]{1}$")]),
      'oArrival': new FormControl(null, Validators.required),
      'oDeparture': new FormControl(null, Validators.required),
      'oArrivalDateNTime': new FormControl(null, Validators.required),
      'oDepartureDateNTime': new FormControl(null, Validators.required)

    }, {
      validators: [arrivalDatesValidator, arrivalDepartureValidator],
      updateOn: "blur"
    });


  }

  ngOnDestroy() {
    this.flightArraySubscription?.unsubscribe();
    this.airportsListSubscription?.unsubscribe();
  }

  onDeleteFlight(flight_id: number, searchForm: NgForm) {
    this.dataService.removeFlight(flight_id).subscribe({
      next: (response) => {
        if (response.status === HttpStatusCodesFMS.ENTRY_NOT_FOUND) {
          alert("This flight already deleted by someone!\nYou are retrieving latest update!");
        }
        else{
          alert("Flight deleted Successfully!");
        }

        this.dataService.fetchFlights();
        searchForm.reset();
      },
      error: () => alert("Unexpected error occurred!")
    });
  }


  onEditFlight(flight: Flight) {
    this.isEditMode = true;
    this.formTempData = {
      'oId': flight.flightId,
      'oFlightNumber': flight.flightNumber,
      'oArrival': flight.arrival,
      'oDeparture': flight.departure,
      'oArrivalDateNTime': flight.arrivalDate + "T" + flight.arrivalTime,
      'oDepartureDateNTime': flight.departureDate + "T" + flight.departureTime
    }
    this.tempFlight = flight;
    this.overlayForm.reset(this.formTempData);
    this.isOverlayShow = !this.isOverlayShow;
  }


  onClickAddFlight() {
    this.isEditMode = false;
    this.isOverlayShow = !this.isOverlayShow;
    this.overlayForm.reset();
  }

  onClickSearch(f: NgForm) {
    let value = f.value;
    this.dataService.searchFlights(value);
  }

  onClearSearch(f: NgForm) {
    f.reset({
      fNumber: "",
      fDeparture: "",
      fArrival: "",
      fDepartureDate: "",
      fArrivalDate: "",
      fDepartureTime: "",
      fArrivalTime: ""
    });
    this.dataService.fetchFlights();
  }

  onCancelEdit() {
    if (this.overlayForm.pristine) {
      this.isOverlayShow = !this.isOverlayShow;
      this.overlayForm.reset();
    } else {
      if (confirm("Are you want to exit from editing?")) {
        this.isOverlayShow = !this.isOverlayShow;
        this.overlayForm.reset();
      }
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
    let tempSubscription !: Observable<HttpResponse<Flight>>;

    if (this.isEditMode) {
      tempSubscription = this.dataService.updateFlight(value, this.tempFlight);
    } else {
      tempSubscription = this.dataService.addFlight(value);
    }

    tempSubscription.subscribe({
      next: (response) => {
        if (response.status === HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND) {
          alert("Flight number already has flight on given departure date!");
        }
        else if (response.status === HttpStatusCodesFMS.VERSION_MISMATCHED) {
          alert("This flight already updated by someone!\nYou are retrieving latest update!");
          this.resetFormAndFetchData();
        }
        else if(response.status === HttpStatusCodesFMS.ROUTE_DOESNT_EXIST){
          alert("Route does not exists! Can not create the flight");
        }
        else {
          let successMessage = this.isEditMode ? "edited" : "created";
          alert("Flight " + successMessage + " Successfully!");
          this.resetFormAndFetchData();
        }
      },
      error: () => {
        alert("Unexpected Error occurred! Please try again!");
      }
    })
  }

  isFormEmpty(formValues:{}){
    return Object.values(formValues).every(attr => attr === "" || attr === null);
  }
  resetFormScreeMode() {
    this.isOverlayShow = !this.isOverlayShow;
    this.isEditMode = false;
    this.overlayForm.reset();
  }

  private resetFormAndFetchData() {
    this.resetFormScreeMode();
    this.dataService.fetchFlights();
  }

}
