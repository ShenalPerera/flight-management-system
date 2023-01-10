import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {FormComponent} from "../form/form.component";
import {MatExpansionPanel} from "@angular/material/expansion";
// import {ALL_ROUTES} from "../shared/routes";

@Component({
  selector: 'tr [app-entry]',
  templateUrl: './entry.component.html',
  styleUrls: ['./entry.component.scss'],
  viewProviders: [MatExpansionPanel]
})
export class EntryComponent {

  @Input() routeID !: number;
  @Input() departure !: string;
  @Input() destination !: string;
  @Input() mileage !: number;
  @Input() durationH !: number;

  displayEntry() {
    console.log("You clicked "+this.routeID+' '+this.departure);
  }

  @Output() sendToBeDeletedRecordEvent = new EventEmitter<number>();

  deleteItem() {
    if (confirm(`Do you want to delete this route ?\n
    RouteID        : ${this.routeID}\n
    Departure      : ${this.departure}\n
    Destination    : ${this.destination}\n
    Mileage        : ${this.mileage}\n
    Duration(hours): ${this.durationH}`)) {
      console.log("Do deletion with ID: "+this.routeID);
      this.sendToBeDeletedRecordEvent.emit(this.routeID);
    }
  }

  constructor(public dialog: MatDialog) {
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(FormComponent, {
      width: '315px',
      height: 'auto',
      data: {routeID: this.routeID, departure: this.departure, destination: this.destination, mileage: this.mileage, durationH: this.durationH},
      disableClose: true
    });

    // dialogRef.beforeClosed().subscribe(res=>{
    //   if (confirm('Changes will be discarded.')) {
    //
    //   }
    // })

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      // this.animal = result;
    });
  }

  @Output() sendItemsEvent = new EventEmitter<[number, string, string, number, number]>();

  sendItemsToParent() {
    this.sendItemsEvent.emit([this.routeID, this.departure, this.destination, this.mileage, this.durationH]);
    console.log("items sent to parent");
  }

}
