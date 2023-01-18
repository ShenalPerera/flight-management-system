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
  departingLocations?: string[];
  arrivingLocations?: string[];
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
    this.fareService.getLocations().subscribe((data: string[]) => {
      this.departingLocations = data,
      this.arrivingLocations = data
    });
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
          createEvent: this.createEvent,
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
      this.fareService.createEntry(this.editedEntry);
      this.filterData();
    } else {
      this.fareService.editEntry(this.editedEntry);
    }
    this.handleEditClear();
  }
}
