import { Component } from '@angular/core';
import { MatDialog } from "@angular/material/dialog";
import { FareFormComponent } from "./fare-form/fare-form.component";
import { Entry } from "./shared/entry.model";

@Component({
  selector: 'app-fares-screen',
  templateUrl: './fares-screen.component.html',
  styleUrls: ['./fares-screen.component.scss']
})
export class FaresScreenComponent {
  data: Entry[] = [
    {id:1, departure:"Colombo", arrival:"Dubai", fare:50},
    {id:2, departure:"Colombo", arrival:"Sydney", fare:75},
    {id:3, departure:"Dubai", arrival:"Colombo", fare:50},
    {id:4, departure:"Colombo", arrival:"New York", fare:150},
    {id:5, departure:"New York", arrival:"Sydney", fare:225},
    {id:6, departure:"New York", arrival:"Colombo", fare:150},
    {id:7, departure:"London", arrival:"Colombo", fare:125},
    {id:8, departure:"Dubai", arrival:"London", fare:80},
    {id:9, departure:"Paris", arrival:"Sydney", fare:185},
    {id:10, departure:"New York", arrival:"Paris", fare:135},
  ];
  departingLocations: string[] = ['Colombo', 'Dubai', 'Sydney'];
  arrivingLocations: string[] = ['Colombo', 'Dubai', 'Sydney'];
  searchedData: Entry[] = this.data;
  searchCriteria = {departure: "", arrival: ""};
  editedEntry: Entry = {id: 0, departure: "", arrival: "", fare: 0};
  createEvent: boolean = true;
  isDuplicate: boolean = false;
  currentId: number = this.data.length;

  constructor(public dialog: MatDialog) {}

  generateLocations() {
    this.departingLocations = [...new Set(this.data.map(item => item.departure))];
    this.arrivingLocations = [...new Set(this.data.map(item => item.arrival))];
  }
  filterData(){
    this.searchedData = this.data.filter(x =>
      (this.searchCriteria.departure === "" || this.searchCriteria.departure === x.departure) &&
      (this.searchCriteria.arrival === "" || this.searchCriteria.arrival === x.arrival)
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
      this.data.forEach((value, index) => {
        if (value.id == entry.id)
          this.data.splice(index, 1);
      })
    }
    this.filterData();
  }
  handleDuplicate() {
    this.data.forEach((value) => {
      if ((value.departure === this.editedEntry.departure) && (value.arrival === this.editedEntry.arrival))
        this.isDuplicate = true;
    })
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
    this.data.forEach((value) => {
      if (value.id == this.editedEntry.id) {
        value.departure = this.editedEntry.departure;
        value.arrival = this.editedEntry.arrival;
        value.fare = this.editedEntry.fare;
      }
    })
  }
  createSubmitted() {
    if (confirm("Do you want to create the fare of the route, from "+
      this.editedEntry.departure+" to "+this.editedEntry.arrival+" as "+this.editedEntry.fare+"?")) {
      this.handleDuplicate();
      if (this.isDuplicate) {
        alert("The entry is already in the database!");
        this.isDuplicate = false;
      }
      else {
        this.data.push({
          id: ++this.currentId,
          departure: this.editedEntry.departure,
          arrival: this.editedEntry.arrival,
          fare: this.editedEntry.fare
        });
      }
    }
  }
}
