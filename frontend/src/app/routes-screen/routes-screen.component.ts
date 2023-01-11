import {Component, Inject, OnInit} from '@angular/core';
import {Route} from './models/route';

import {RouteService} from "./services/route.service";
import {MatDialog} from "@angular/material/dialog";
import {FormComponent} from "./form/form.component";
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
  searchedData: Route[] = this.ALL_ROUTES;


  constructor(private routeService: RouteService, public dialog: MatDialog) {
  }

  ngOnInit() {
    this.ALL_ROUTES = this.routeService.getRoutes();
    this.searchedData = this.ALL_ROUTES;

    let listValues = this.routeService.setDeparturesAndDestinations(this.departuresList, this.destinationsList);
    this.departuresList = listValues.dpList;
    this.destinationsList = listValues.dsList;


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
    const dialogRef = this.dialog.open(FormComponent, {
      width: '315px',
      height: 'auto',
      data: {
        route: {routeID: NaN, departure: '', destination: '', mileage: 0, durationH: 0},
        type: 'create'
      },
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


  filterData(){
    this.searchedData = this.ALL_ROUTES.filter(
      x => (this.searchFormDeparture === "" || this.searchFormDeparture === x.departure)
        && (this.searchFormDestination === "" || this.searchFormDestination === x.destination))
  }




}
