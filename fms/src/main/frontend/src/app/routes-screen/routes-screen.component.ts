import {Component, OnInit} from '@angular/core';
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

  constructor(private routeService: RouteService, public dialog: MatDialog) {
  }

  ngOnInit() {
    this.routeService.getRoutesFromBackend()
      .subscribe((resp)=>{
        this.ALL_ROUTES = resp;
        let listValues = this.routeService.initializeDeparturesAndDestinations(this.departuresList, this.destinationsList, resp);
        this.departuresList = listValues.dpList;
        this.destinationsList = listValues.dsList;
      });
    // this.ALL_ROUTES = this.routeService.getRoutes();

    // let listValues = this.routeService.setDeparturesAndDestinations(this.departuresList, this.destinationsList, resp);
    // this.departuresList = listValues.dpList;
    // this.destinationsList = listValues.dsList;


  }

  clearInputs() {
    this.searchFormDeparture = '';
    this.searchFormDestination = '';
    this.ALL_ROUTES = this.routeService.getRoutes();
  }

  updateDropdown() {
    let listValues = this.routeService.setDeparturesAndDestinations(this.departuresList, this.destinationsList);
    this.departuresList = listValues.dpList;
    this.destinationsList = listValues.dsList;
  }

  openDialogToCreate(): void {
    this.clearInputs();
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
    this.routeService.deleteRecord(data);
    this.filterData();
  }


  filterData(){
    this.ALL_ROUTES = this.routeService.filterData(this.searchFormDeparture, this.searchFormDestination);
  }


}
