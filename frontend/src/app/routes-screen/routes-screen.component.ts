import {Component, Inject, OnInit} from '@angular/core';
import {Route} from './models/route';
import {FormControl, NgForm} from "@angular/forms";
import {Observable} from "rxjs";
import {map, startWith} from "rxjs/operators";


@Component({
  selector: 'app-routes-screen',
  templateUrl: './routes-screen.component.html',
  styleUrls: ['./routes-screen.component.scss']
})
export class RoutesScreenComponent implements OnInit{
  ALL_ROUTES: Route[] = [
    {routeID: 1, departure: "Sri Lanka", destination: "Dubai", mileage: 1223.45, durationH: 12, durationM: 30},
    {routeID: 2, departure: "Sri Lanka", destination: "Dubai", mileage: 1223.45, durationH: 12, durationM: 30},
    {routeID: 3, departure: "Sri Lanka", destination: "Dubai", mileage: 1223.45, durationH: 12, durationM: 30},
    {routeID: 4, departure: "Sri Lanka", destination: "New York", mileage: 1223.45, durationH: 12, durationM: 20}
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



  // **************************
  myControl = new FormControl('');
  options: string[] = ['One', 'Two', 'Three'];
  filteredOptions !: Observable<string[]>;

  ngOnInit() {
    this.filteredOptions = this.myControl.valueChanges.pipe(
      startWith(''),
      map(value => this._filter(value || '')),
    );
    this.ALL_ROUTES.forEach((route)=>{
      this.options.push(route.departure);
    })
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.options.filter(option => option.toLowerCase().includes(filterValue));
  }
}
