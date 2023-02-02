import {Component, OnInit} from '@angular/core';
import {Route} from './models/route';

import {RouteService} from "./services/route.service";
import {MatDialog} from "@angular/material/dialog";
import {FormComponent} from "./form/form.component";
import {MatExpansionPanel} from "@angular/material/expansion";
import {AirportsHandleService} from "../services/airports-handle.service";
import {HttpStatusCodesFMS} from "../http-status-codes-fms/httpStatusCodes.enum";


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
  airports: string[] = [];
  departuresList: string[] = [];
  destinationsList: string[] = [];

  constructor(private routeService: RouteService, public dialog: MatDialog, private airportsHandleService: AirportsHandleService) {
  }

  ngOnInit() {
    this.routeService.getRoutesFromBackend()
      .subscribe({next: (resp)=>{
        this.ALL_ROUTES = resp;
        this.generateLocations();


        // let listValues = this.routeService.initializeDeparturesAndDestinations(this.departuresList, this.destinationsList, resp);
        // this.departuresList = listValues.dpList;
        // this.destinationsList = listValues.dsList;
        //
        // console.log(typeof this.ALL_ROUTES[0].modifiedDateTime);
      },
      error: (e)=>{
        alert("Something Went Wrong In Retrieving Data. Try Again.");
      }});

  }

  generateLocations(): void {
    this.airportsHandleService.getAirportsList().subscribe(response => {
      localStorage.setItem("airports",JSON.stringify(response));
      this.airports = response;
    });
  }

  clearInputs() {
    this.searchFormDeparture = '';
    this.searchFormDestination = '';
    this.routeService.getRoutesFromBackend()
      .subscribe({ next: (resp)=>{
        this.ALL_ROUTES = resp;
      },
      error: (e)=>{
        alert("Something Went Wrong In Retrieving Data. Try Again.");
      }});
  }

  // updateDropdown() {
  //   let listValues = this.routeService.initializeDeparturesAndDestinations(this.departuresList, this.destinationsList, this.ALL_ROUTES);
  //   this.departuresList = listValues.dpList;
  //   this.destinationsList = listValues.dsList;
  // }

  openDialogToCreate(): void {
    this.clearInputs();
    const dialogRef = this.dialog.open(FormComponent, {
      width: '315px',
      height: 'auto',
      data: {
        route: {routeID: NaN, departure: '', destination: '', mileage: 0, durationH: 0, status: NaN, version: NaN},
        airports: this.airports,
        type: 'create'
      },
      disableClose: true
    });

    dialogRef.afterClosed()
      .subscribe((resp)=>{
        if (resp.reloadData) {
          this.routeService.getRoutesFromBackend()
            .subscribe({next: (resp)=>{
                this.ALL_ROUTES = resp;
                // this.updateDropdown()
              },
            error: (e)=>{
              alert("Something Went Wrong In Retrieving Data. Try Again.");
            }})
        }
      })



  }

  dataPopulate(data: boolean) {
    if (data) {
      this.routeService.getRoutesFromBackend()
        .subscribe({next: (resp)=>{
            this.ALL_ROUTES = resp;
            // this.updateDropdown();
          },
        error: (e)=>{
          alert("Something Went Wrong In Retrieving Data. Try Again.");
        }})
    }
  }

  deleteRouteInBackend(data: number) {
    this.routeService.deleteRecordInBackend(data)
      .subscribe({
        next: (resp)=>{
          this.searchFormDeparture = '';
          this.searchFormDestination = '';
          if (resp.status == HttpStatusCodesFMS.ENTRY_NOT_FOUND) {
            alert("Sorry, that route has been already deleted.");
          } else if (resp.status == HttpStatusCodesFMS.CANNOT_BE_EXECUTED) {
            alert("Sorry! This route cannot be deactivated.");
          }
          else {
            alert("The route has been successfully deleted.");
          }
          this.routeService.getRoutesFromBackend()
            .subscribe({next: (resp)=>{
                this.ALL_ROUTES = resp;
              },
              error: (e)=>{
                alert("Something Went Wrong In Retrieving Data. Try Again.");
              }})
        },
        error: (e)=>{
          alert("Something Went Wrong In Deletion. Try Again.");
        }
      })
  }

  filterDataFromBackend(){
    this.routeService.fetchSpecificRoutesFromBackend(this.searchFormDeparture, this.searchFormDestination)
      .subscribe({next: (resp)=>{
          this.ALL_ROUTES = resp;
        },
        error: (e)=>{
          alert("Something Went Wrong In Retrieving Data. Try Again.");
        }})
  }



}
