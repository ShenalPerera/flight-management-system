import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {FormComponent} from "../form/form.component";
import {MatExpansionPanel} from "@angular/material/expansion";
import {Route} from "../models/route";

@Component({
  selector: 'tr [app-entry]',
  templateUrl: './entry.component.html',
  styleUrls: ['./entry.component.scss'],
  viewProviders: [MatExpansionPanel]
})
export class EntryComponent {

  @Input() route !: Route;
  @Input() rowNumber !: number;
  @Output() sendToBeDeletedRecordEvent = new EventEmitter<number>();
  @Output() dataPopulateEvent = new EventEmitter<boolean>();

  constructor(public dialog: MatDialog) {
  }

  getHours(): number {
    return Math.floor(this.route.durationH);
  }
  getMinutes(): number {
    return Math.ceil((this.route.durationH - Math.floor(this.route.durationH))*60);
  }

  deleteItem() {
    if (confirm(`Do you want to delete this route ?\n
    Departure          : ${this.route.departure.toUpperCase()}\n
    Destination        : ${this.route.destination.toUpperCase()}\n
    Mileage              : ${this.route.mileage}\n
    Duration(hours) : ${this.route.durationH}`)) {

      this.sendToBeDeletedRecordEvent.emit(this.route.routeID);
    }
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(FormComponent, {
      width: '315px',
      height: 'auto',
      data: {
        route: {routeID: this.route.routeID, departure: this.route.departure, destination: this.route.destination,
          mileage: this.route.mileage, durationH: this.route.durationH, version: this.route.version},
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
