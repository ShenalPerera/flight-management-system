import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {FormComponent} from "../form/form.component";
import {MatExpansionPanel} from "@angular/material/expansion";
import {Route} from "../models/route";
import {RouteService} from "../services/route.service";

@Component({
  selector: 'tr [app-entry]',
  templateUrl: './entry.component.html',
  styleUrls: ['./entry.component.scss'],
  viewProviders: [MatExpansionPanel]
})
export class EntryComponent implements OnInit{

  @Input() route !: Route;
  @Input() rowNumber !: number;
  @Output() sendToBeDeletedRecordEvent = new EventEmitter<number>();
  @Output() dataPopulateEvent = new EventEmitter<boolean>();

  numOfHours !: number;
  numOfMinutes !: number;
  constructor(private routeService: RouteService, public dialog: MatDialog) {
  }

  ngOnInit() {
    // this.numOfHours = Math.floor(this.route.durationH);
    // this.numOfMinutes = Math.ceil((this.route.durationH - Math.floor(this.route.durationH))*60);
  }

  getHours(duration: number): number {
    return Math.floor(this.route.durationH);
  }
  getMinutes(duration: number): number {
    return Math.ceil((this.route.durationH - Math.floor(this.route.durationH))*60);
  }

  deleteItem() {
    if (confirm(`Do you want to delete this route ?\n
    RouteID        : ${this.route.routeID}\n
    Departure      : ${this.route.departure.toUpperCase()}\n
    Destination    : ${this.route.destination.toUpperCase()}\n
    Mileage        : ${this.route.mileage}\n
    Duration(hours): ${this.route.durationH}`)) {

    this.routeService.confirmBeforeDelete(this.route.routeID).subscribe({
      next: (resp)=>{
        if (resp[0]>0 || resp[1]>0) {
          if (confirm(`NOTE:\n
          Number of combinations with Fares   -> ${resp[0]}\n
          Number of combinations with Flights -> ${resp[1]}\n
          Do you want to proceed ?`)) {
            this.sendToBeDeletedRecordEvent.emit(this.route.routeID);
          }
        } else {
          this.sendToBeDeletedRecordEvent.emit(this.route.routeID);
        }
      }
    })

      // this.sendToBeDeletedRecordEvent.emit(this.route.routeID);
    }
  }

  confirmBeforeDelete() {
    // this.routeService.confirmBeforeDelete(this.route.routeID).subscribe({
    //   next: (resp): Boolean=>{
    //     if (resp[0]>0) {
    //       if (confirm(`NOTE: There are ${resp[0]} number of matched locations in fares.\n
    //       Do you want to proceed ?`)) {
    //         return true;
    //       } else {
    //         return false;
    //       }
    //     }
    //   }
    // })
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(FormComponent, {
      width: '315px',
      height: 'auto',
      data: {
        route: {routeID: this.route.routeID, departure: this.route.departure, destination: this.route.destination, mileage: this.route.mileage, durationH: this.route.durationH, version: this.route.version},
        // airports: [],
        type: 'edit'
      },
      disableClose: true
    });

    dialogRef.afterClosed()
      .subscribe((resp)=>{
        if (resp.reloadData) {
          this.dataPopulateEvent.emit(true);
        }
      })

  }

}
