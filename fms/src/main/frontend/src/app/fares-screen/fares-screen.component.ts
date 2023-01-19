import { Component, OnInit } from '@angular/core';
import { MatDialog } from "@angular/material/dialog";
import { FareFormComponent } from "./fare-form/fare-form.component";
import { Entry } from "./shared/entry.model";
import { FareService } from "./services/fare.service";

@Component({
  selector: 'app-fares-screen',
  templateUrl: './fares-screen.component.html',
  styleUrls: ['./fares-screen.component.scss']
})
export class FaresScreenComponent implements OnInit {
  departingLocations?: string[];
  arrivingLocations?: string[];
  searchCriteria = {departure: "", arrival: ""};
  searchedData?: Entry[];
  editedEntry: Entry = {id: 0, departure: "", arrival: "", fare: 0};
  createEvent: boolean = true;

  constructor(
    private fareService: FareService,
    public dialog: MatDialog
  ) {}

  ngOnInit() {
    this.getEntries();
    this.generateLocations();
  }
  generateLocations() {
    this.fareService.getLocations().subscribe((data: string[]) => {
      this.departingLocations = data,
      this.arrivingLocations = data
    });
  }
  getEntries() {
    this.fareService.getEntries().subscribe((data: Entry[]) => this.searchedData = data);
  }
  filterData(){
    this.fareService.filterDataService(this.searchCriteria.departure, this.searchCriteria.arrival)
      .subscribe((data: Entry[]) => this.searchedData = data);
  }

  handleSearchClear() {
    this.searchCriteria = {departure: "", arrival: ""};
    this.getEntries();
  }
  handleEditClear() {
    this.editedEntry = {id: 0, departure: "", arrival: "", fare: 0};
  }
  handleDelete(entry: Entry) {
    if (confirm("Do you want to delete the entry from "+entry.departure+" to "+entry.arrival+"?")) {
      this.fareService.deleteEntry(entry.id).subscribe((data: number) => this.filterData() );
      // this.filterData();
    }
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
        // this.submitted();
      }
      this.filterData();
      // this.handleEditClear();
    });
  }

  // submitted() {
  //   if (this.createEvent) {
  //     this.fareService.createEntry(this.editedEntry).subscribe((data) => this.searchedData?.push(data));
  //     this.fareService.createEntry(this.editedEntry);
  //     this.filterData();
  //   } else {
  //     this.fareService.editEntry(this.editedEntry);
  //   }
  //   this.handleEditClear();
  // }
}
