import {Component, Inject, OnInit} from '@angular/core';
import {Route} from './models/route';
import {FormControl, FormGroup, NgForm} from "@angular/forms";
import {Observable} from "rxjs";
import {map, startWith} from "rxjs/operators";


@Component({
  selector: 'app-routes-screen',
  templateUrl: './routes-screen.component.html',
  styleUrls: ['./routes-screen.component.scss']
})
export class RoutesScreenComponent implements OnInit{
  ALL_ROUTES: Route[] = [
    {routeID: 1, departure: "SL", destination: "Dubai", mileage: 1223.45, durationH: 12, durationM: 30},
    {routeID: 2, departure: "NY", destination: "Dubai", mileage: 1223.45, durationH: 12, durationM: 30},
    {routeID: 3, departure: "LA", destination: "Dubai", mileage: 1223.45, durationH: 12, durationM: 30},
    {routeID: 4, departure: "CA", destination: "New York", mileage: 1223.45, durationH: 12, durationM: 20},
    {routeID: 5, departure: "JP", destination: "Sydney", mileage: 1223.45, durationH: 12, durationM: 20}
  ];

  searchFormDeparture: string = '';
  searchFormDestination: string = '';
  departuresList: string[] = [];
  destinationsList: string[] = [];

  onSubmit(f: NgForm) {
    console.log(f.value['departure']);
    console.log(f.value['destination']);

    // after doing the operation call the clearInput function
  }

  clearInputs() {
    this.searchFormDeparture = '';
    this.searchFormDestination = '';
    this.searchedData = this.ALL_ROUTES;
  }



  formDataInRouteScreen !: [number, string, string, number, number, number];

  // getFormData(data: [number, string, string, number, number, number]) {
  //   console.log('screen received: ' + data);
  //   this.formDataInRouteScreen = data;
  // }

  gotData(data: [number, string, string, number, number, number]) {
    console.log(data);
    this.formDataInRouteScreen = data;
  }

  deleteRecord(data: number) {
    this.ALL_ROUTES = this.ALL_ROUTES.filter((route)=>{
      return route.routeID !== data;
    });
    console.log('deleted with the id: '+data);
  }

  filterByForm(f: NgForm) {
    console.log(f.value);
  }

  searchedData: Route[] = this.ALL_ROUTES;
  filterData(){
    this.searchedData = this.ALL_ROUTES.filter(
      x => (this.searchFormDeparture === "" || this.searchFormDeparture === x.departure)
        && (this.searchFormDestination === "" || this.searchFormDestination === x.destination))
  }


  // **************************

  myDepartureControl = new FormControl('');
  myDestinationControl = new FormControl('');

  filteredDepartures !: Observable<string[]>;
  filteredDestinations !: Observable<string[]>;

  ngOnInit() {
    this.filteredDepartures = this.myDepartureControl.valueChanges.pipe(
      startWith(''),
      map(value => this._filterDeparture(value || '')),
    );

    this.filteredDestinations = this.myDestinationControl.valueChanges.pipe(
      startWith(''),
      map(value => this._filterDestination(value || '')),
    );

    this.ALL_ROUTES.forEach((route)=>{
      if (!(this.departuresList.includes(route.departure))) {
        this.departuresList.push(route.departure);
      }
      if (!(this.destinationsList.includes(route.destination))) {
        this.destinationsList.push(route.destination);
      }


    });
    console.log('destination list: '+this.destinationsList);
  }

  private _filterDeparture(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.departuresList.filter(departure => departure.toLowerCase().includes(filterValue));
  }

  private _filterDestination(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.destinationsList.filter(destination => destination.toLowerCase().includes(filterValue));
  }
}
