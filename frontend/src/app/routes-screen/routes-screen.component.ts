import {Component, Inject, OnInit} from '@angular/core';
import {Route} from './models/route';
import {FormControl, FormGroup, NgForm} from "@angular/forms";
import {Observable} from "rxjs";
import {map, startWith} from "rxjs/operators";

import {RouteService} from "./services/route.service";
import {MatDialog} from "@angular/material/dialog";
import {FormComponent} from "./form/form.component";
import {CreateFormComponent} from "./create-form/create-form.component";


@Component({
  selector: 'app-routes-screen',
  templateUrl: './routes-screen.component.html',
  styleUrls: ['./routes-screen.component.scss']
})
export class RoutesScreenComponent implements OnInit{
  ALL_ROUTES !: Route[];

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

  updateDropdown() {
    let listValues = this.routeService.setDeparturesAndDestinations(this.departuresList, this.destinationsList);
    this.departuresList = listValues.dpList;
    this.destinationsList = listValues.dsList;
  }

  openDialogToCreate(): void {
    const dialogRef = this.dialog.open(CreateFormComponent, {
      width: '500px',
      data: {routeID: NaN, departure: '', destination: '', mileage: NaN, durationH: NaN, durationM: NaN}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      // this.animal = result;
    });
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
    this.ALL_ROUTES = this.routeService.deleteRecord(data, this.searchedData);
    this.searchedData = this.ALL_ROUTES;

    let listValues = this.routeService.setDeparturesAndDestinations(this.departuresList, this.destinationsList);
    this.departuresList = listValues.dpList;
    this.destinationsList = listValues.dsList;
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

  constructor(private routeService: RouteService, public dialog: MatDialog) {
  }

  ngOnInit() {
    this.ALL_ROUTES = this.routeService.getRoutes();
    this.searchedData = this.ALL_ROUTES;
    // console.log('all routes after using service '+this.routeService.getRoutes()[0].routeID);

    this.filteredDepartures = this.myDepartureControl.valueChanges.pipe(
      startWith(''),
      map(value => this._filterDeparture(value || '')),
    );

    this.filteredDestinations = this.myDestinationControl.valueChanges.pipe(
      startWith(''),
      map(value => this._filterDestination(value || '')),
    );

    // this.setDeparturesAndDestinations();
    let listValues = this.routeService.setDeparturesAndDestinations(this.departuresList, this.destinationsList);
    this.departuresList = listValues.dpList;
    this.destinationsList = listValues.dsList;


    // this.searchedData.forEach((route)=>{
    //   if (!(this.departuresList.includes(route.departure))) {
    //     this.departuresList.push(route.departure);
    //   }
    //   if (!(this.destinationsList.includes(route.destination))) {
    //     this.destinationsList.push(route.destination);
    //   }
    //
    //
    // });
    console.log('destination list: '+this.destinationsList);
  }

  // setDeparturesAndDestinations() {
  //   this.departuresList = [];
  //   this.destinationsList = [];
  //   this.searchedData.forEach((route)=>{
  //     if (!(this.departuresList.includes(route.departure))) {
  //       this.departuresList.push(route.departure);
  //     }
  //     if (!(this.destinationsList.includes(route.destination))) {
  //       this.destinationsList.push(route.destination);
  //     }
  //
  //
  //   });
  // }

  private _filterDeparture(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.departuresList.filter(departure => departure.toLowerCase().includes(filterValue));
  }

  private _filterDestination(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.destinationsList.filter(destination => destination.toLowerCase().includes(filterValue));
  }
}
