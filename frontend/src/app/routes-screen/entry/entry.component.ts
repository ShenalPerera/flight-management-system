import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {FormComponent} from "../form/form.component";
import {MatExpansionPanel} from "@angular/material/expansion";
import {Route} from "../models/route";
// import {ALL_ROUTES} from "../shared/routes";

@Component({
  selector: 'tr [app-entry]',
  templateUrl: './entry.component.html',
  styleUrls: ['./entry.component.scss'],
  viewProviders: [MatExpansionPanel]
})
export class EntryComponent {

  @Input() route !: Route;



  @Output() sendToBeDeletedRecordEvent = new EventEmitter<number>();

  constructor(public dialog: MatDialog) {
  }

  deleteItem() {
    if (confirm(`Do you want to delete this route ?\n
    RouteID        : ${this.route.routeID}\n
    Departure      : ${this.route.departure}\n
    Destination    : ${this.route.destination}\n
    Mileage        : ${this.route.mileage}\n
    Duration(hours): ${this.route.durationH}`)) {
      this.sendToBeDeletedRecordEvent.emit(this.route.routeID);
    }
  }



  openDialog(): void {
    const dialogRef = this.dialog.open(FormComponent, {
      width: '315px',
      height: 'auto',
      data: {
        route: {routeID: this.route.routeID, departure: this.route.departure, destination: this.route.destination, mileage: this.route.mileage, durationH: this.route.durationH},
        type: 'edit'
      },
      disableClose: true
    });

  }

}
