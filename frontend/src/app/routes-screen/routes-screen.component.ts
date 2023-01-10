import {Component, Inject, OnInit} from '@angular/core';
import {Route} from './models/route';
import {FormControl, FormGroup, NgForm} from "@angular/forms";
import {Observable} from "rxjs";
import {map, startWith} from "rxjs/operators";

import {RouteService} from "./services/route.service";
import {MatDialog} from "@angular/material/dialog";
import {FormComponent} from "./form/form.component";
import {CreateFormComponent} from "./create-form/create-form.component";
import {MatExpansionPanel} from "@angular/material/expansion";


@Component({
  selector: 'app-routes-screen',
  templateUrl: './routes-screen.component.html',
  styleUrls: ['./routes-screen.component.scss'],
  viewProviders: [MatExpansionPanel]
})
export class RoutesScreenComponent implements OnInit{
  ALL_ROUTES !: Route[];

  searchFormDeparture: string = '';
  searchFormDestination: string = '';
  departuresList: string[] = [];
  destinationsList: string[] = [];


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
      width: '315px',
      height: 'auto',
      data: {routeID: NaN, departure: '', destination: '', mileage: 0, durationH: 0},
      disableClose: true
    });


  }


  deleteRecord(data: number) {
    this.ALL_ROUTES = this.routeService.deleteRecord(data, this.searchedData);
    this.searchedData = this.ALL_ROUTES;

    let listValues = this.routeService.setDeparturesAndDestinations(this.departuresList, this.destinationsList);
    this.departuresList = listValues.dpList;
    this.destinationsList = listValues.dsList;
  }

  searchedData: Route[] = this.ALL_ROUTES;
  filterData(){
    this.searchedData = this.ALL_ROUTES.filter(
      x => (this.searchFormDeparture === "" || this.searchFormDeparture === x.departure)
        && (this.searchFormDestination === "" || this.searchFormDestination === x.destination))
  }


  constructor(private routeService: RouteService, public dialog: MatDialog) {
  }

  ngOnInit() {
    this.ALL_ROUTES = this.routeService.getRoutes();
    this.searchedData = this.ALL_ROUTES;

    let listValues = this.routeService.setDeparturesAndDestinations(this.departuresList, this.destinationsList);
    this.departuresList = listValues.dpList;
    this.destinationsList = listValues.dsList;


  }

}
