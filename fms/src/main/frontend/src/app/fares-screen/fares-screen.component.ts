import { Component, OnInit } from '@angular/core';
import { MatDialog } from "@angular/material/dialog";
import { FareFormComponent } from "./fare-form/fare-form.component";
import { Fare } from "./shared/entry.model";
import { FareService } from "./services/fare.service";
import {AirportsHandleService} from "../services/airports-handle.service";
import {HttpStatusCodesFMS} from "../http-status-codes-fms/httpStatusCodes.enum";

@Component({
  selector: 'app-fares-screen',
  templateUrl: './fares-screen.component.html',
  styleUrls: ['./fares-screen.component.scss']
})
export class FaresScreenComponent implements OnInit {
  airports?: string[];
  searchCriteria = {departure: "", arrival: ""};
  searchedData?: Fare[];
  editedEntry?: Fare;
  createEvent: boolean = true;

  constructor(
    private fareService: FareService,
    private airportsHandleService: AirportsHandleService,
    public dialog: MatDialog
  ) {}

  ngOnInit() {
    this.getAllEntries();
    this.generateAirports();
    this.editedEntry = {fareId: 0, departure: "", arrival: "", fare: 0, version: 0};
  }
  generateAirports(): void {
    this.airportsHandleService.getAirportsList().subscribe(response => {
      localStorage.setItem("airports",JSON.stringify(response));
      this.airports = response;
    });
  }
  getAllEntries(): void {
    this.fareService.getAllEntries().subscribe((data: Fare[]) => this.searchedData = data);
  }
  filterEntries(): void {
    this.fareService.getFilteredEntries(this.searchCriteria.departure, this.searchCriteria.arrival)
      .subscribe((data: Fare[]) => this.searchedData = data);
  }
  handleSearchClear(): void {
    this.searchCriteria = {departure: "", arrival: ""};
    this.getAllEntries();
  }
  handleDelete(entry: Fare): void {
    if (confirm(`
    Do you want to delete this fare ?\n
    Departure : ${entry.departure.toUpperCase()}\n
    Arrival       : ${entry.arrival.toUpperCase()}\n
    Fare          : ${entry.fare}
    `)) {
      this.fareService.deleteEntry(entry.fareId).subscribe({
        next: (response) => {
          if (response.status == HttpStatusCodesFMS.ENTRY_NOT_FOUND)
            alert("Someone else has already deleted the fare!")
          else
            alert("The Fare has been successfully deleted!")
          this.filterEntries()
        }
      });
    }
  }
  openForm(entry?: Fare): void {
    if (entry) {
      this.createEvent = false;
      this.editedEntry = entry;
    } else {
      this.createEvent = true;
      this.editedEntry = { fareId: 0, departure: "", arrival: "", fare: null, version: 0};
    }
    const dialogRef = this.dialog.open(FareFormComponent, {
        data: {
          createEvent: this.createEvent,
          entry: {
            fareId: this.editedEntry.fareId,
            departure: this.editedEntry.departure,
            arrival: this.editedEntry.arrival,
            fare: this.editedEntry.fare,
            version: this.editedEntry.version
          },
          airports: this.airports
        },
        disableClose: true
      }
    );
    dialogRef.afterClosed().subscribe( (appliedChanges: boolean) => {
      this.filterEntries();
      if (appliedChanges) {
        if (this.createEvent)
          alert("The Fare has been successfully created!");
        else
          alert("The Fare has been successfully updated!");
      }
    });
  }
}
