import { Component, OnInit } from '@angular/core';
import { MatDialog } from "@angular/material/dialog";
import { FareFormComponent } from "./fare-form/fare-form.component";
import { Entry } from "./shared/entry.model";
import { FareService } from "./services/fare.service";
import {AirportsHandleService} from "../services/airports-handle.service";

@Component({
  selector: 'app-fares-screen',
  templateUrl: './fares-screen.component.html',
  styleUrls: ['./fares-screen.component.scss']
})
export class FaresScreenComponent implements OnInit {
  airports?: string[];
  searchCriteria = {departure: "", arrival: ""};
  searchedData?: Entry[];
  editedEntry?: Entry;
  createEvent: boolean = true;

  constructor(
    private fareService: FareService,
    private airportsHandleService: AirportsHandleService,
    public dialog: MatDialog
  ) {}

  ngOnInit() {
    this.getAllEntries();
    this.generateAirports();
    this.editedEntry = {id: 0, departure: "", arrival: "", fare: 0, createdTimestamp: "", modifiedTimestamp: "", version: 0};
  }
  generateAirports(): void {
    this.airportsHandleService.getAirportsList().subscribe(response => {
      localStorage.setItem("airports",JSON.stringify(response));
      this.airports = response;
    });
  }
  getAllEntries(): void {
    this.fareService.getAllEntries().subscribe((data: Entry[]) => this.searchedData = data);
  }
  filterEntries(): void {
    this.fareService.getFilteredEntries(this.searchCriteria.departure, this.searchCriteria.arrival)
      .subscribe((data: Entry[]) => this.searchedData = data);
  }
  handleSearchClear(): void {
    this.searchCriteria = {departure: "", arrival: ""};
    this.getAllEntries();
  }
  handleDelete(entry: Entry): void {
    if (confirm("Do you want to delete the entry from "+entry.departure+" to "+entry.arrival+"?")) {
      this.fareService.deleteEntry(entry.id).subscribe(() => this.filterEntries() );
    }
  }
  openForm(entry?: Entry): void {
    if (entry) {
      this.createEvent = false;
      this.editedEntry = entry;
    } else {
      this.createEvent = true;
      this.editedEntry = { id: 0, departure: "", arrival: "", fare: 0, createdTimestamp: "", modifiedTimestamp: "", version: 0};
    }
    const dialogRef = this.dialog.open(FareFormComponent, {
        data: {
          createEvent: this.createEvent,
          entry: {
            id: this.editedEntry.id,
            departure: this.editedEntry.departure,
            arrival: this.editedEntry.arrival,
            fare: this.editedEntry.fare,
            createdTimestamp: this.editedEntry.createdTimestamp,
            modifiedTimestamp: this.editedEntry.modifiedTimestamp,
            version: this.editedEntry.version
          },
          airports: this.airports
        },
        disableClose: true
      }
    );
    dialogRef.afterClosed().subscribe( (entriesChanged: boolean) => {
      if (entriesChanged)
        this.filterEntries();
    });
  }
}
