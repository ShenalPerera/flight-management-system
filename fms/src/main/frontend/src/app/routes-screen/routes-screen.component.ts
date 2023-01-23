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
      .subscribe({next: (resp)=>{
        this.ALL_ROUTES = resp;
        let listValues = this.routeService.initializeDeparturesAndDestinations(this.departuresList, this.destinationsList, resp);
        this.departuresList = listValues.dpList;
        this.destinationsList = listValues.dsList;
      },
      error: (e)=>{
        alert("Something Went Wrong In Retrieving Data. Try Again.");
      }});

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

  updateDropdown() {
    let listValues = this.routeService.initializeDeparturesAndDestinations(this.departuresList, this.destinationsList, this.ALL_ROUTES);
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

    dialogRef.afterClosed()
      .subscribe((resp)=>{
        if (resp.reloadData) {
          this.routeService.getRoutesFromBackend()
            .subscribe({next: (resp)=>{
                this.ALL_ROUTES = resp;
                this.updateDropdown()
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
            this.updateDropdown();
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
