import { Component } from '@angular/core';
import { MatDialog } from "@angular/material/dialog";
import { FareFormComponent } from "./fare-form/fare-form.component";
import { Entry } from "./shared/entry.model";
import { FareService } from "./services/fare.service";

@Component({
  selector: 'app-fares-screen',
  templateUrl: './fares-screen.component.html',
  styleUrls: ['./fares-screen.component.scss']
})
export class FaresScreenComponent {
  departingLocations: string[] = ['Colombo', 'Dubai', 'Sydney'];
  arrivingLocations: string[] = ['Colombo', 'Dubai', 'Sydney'];
  searchCriteria = {departure: "", arrival: ""};
  searchedData: Entry[] = this.fareService.filterDataService(
    this.searchCriteria.departure,
    this.searchCriteria.arrival
  )
  editedEntry: Entry = {id: 0, departure: "", arrival: "", fare: 0};
  createEvent: boolean = true;

  constructor(
    private fareService: FareService,
    public dialog: MatDialog
  ) {}

  generateLocations() {
    this.departingLocations = this.fareService.generateDepartingLocations();
    this.arrivingLocations = this.fareService.generateArrivingLocations();
  }
  filterData(){
    this.searchedData = this.fareService.filterDataService(
      this.searchCriteria.departure,
      this.searchCriteria.arrival
    )
  }

  handleSearchClear() {
    this.searchCriteria = {departure: "", arrival: ""};
    this.filterData();
  }
  handleEditClear() {
    this.editedEntry = {id: 0, departure: "", arrival: "", fare: 0};
  }
  handleDelete(entry: Entry) {
    if (confirm("Do you want to delete the entry from "+entry.departure+" to "+entry.arrival+"?")) {
      this.fareService.deleteEntry(entry.id);
    }
    this.filterData();
  }

  openForm(entry?: Entry) {
    if (entry) {
      this.createEvent = false;
      this.editedEntry = entry;
    } else {
      this.createEvent = true;
    }
    const dialogRef = this.dialog.open(FareFormComponent, {
        data: {
          type: entry? "Edit" : "Create",
          entry: {
            id: this.editedEntry.id,
            departure: this.editedEntry.departure,
            arrival: this.editedEntry.arrival,
            fare: this.editedEntry.fare
          },
          departingLocations: this.departingLocations,
          arrivingLocations: this.arrivingLocations
        },
        disableClose: true
      }
    );
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.editedEntry = result;
        this.submitted();
      }
      this.handleEditClear();
    });
  }
  submitted() {
    if (this.createEvent) {
      this.createSubmitted();
      this.filterData();
    } else {
      this.editSubmitted();
    }
    this.handleEditClear();
  }
  editSubmitted() {
    if (this.fareService.isDuplicate(this.editedEntry.departure, this.editedEntry.arrival) != this.editedEntry.id) {
      alert("The entry is already in the database!");
    } else {
      this.fareService.editEntry(this.editedEntry);
    }
  }
  createSubmitted() {
    if (confirm("Do you want to create the fare of the route, from "+
      this.editedEntry.departure+" to "+this.editedEntry.arrival+" as "+this.editedEntry.fare+"?")) {
      if (this.fareService.isDuplicate(this.editedEntry.departure, this.editedEntry.arrival)) {
        alert("The entry is already in the database!");
      }
      else {
        this.fareService.createEntry(this.editedEntry);
      }
    }
  }
}
