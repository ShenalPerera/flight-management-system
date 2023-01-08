import {Component} from '@angular/core';

export interface Entry {
  id: number;
  departure: string;
  arrival: string;
  fare: number;
}

@Component({
  selector: 'app-fares-screen',
  templateUrl: './fares-screen.component.html',
  styleUrls: ['./fares-screen.component.scss']
})

export class FaresScreenComponent {

  searchCriteria: Entry = {id: 0, departure: "", arrival: "", fare: 0};
  editedEntry: Entry = {id: 0, departure: "", arrival: "", fare: 0};

  locations: string[] = ['Colombo', 'Dubai', 'Sydney'];
  data: Entry[] = [
    {id:1, departure:"Colombo", arrival:"Dubai", fare:50},
    {id:2, departure:"Colombo", arrival:"Sydney", fare:75},
    {id:3, departure:"Dubai", arrival:"Colombo", fare:50},
  ];
  searchedData: Entry[] = this.data;
  formDisabled: boolean = true;
  filterData(){
    this.searchedData = this.data.filter(
      x => (this.searchCriteria.departure === "" || this.searchCriteria.departure === x.departure)
        && (this.searchCriteria.arrival === "" || this.searchCriteria.arrival === x.arrival))
  }
  handleSearchClear() {
    this.searchCriteria = {id: 0, departure: "", arrival: "", fare: 0};
    this.filterData();
  }
  handleEditClear() {
    this.editedEntry = {id: 0, departure: "", arrival: "", fare: 0};
    this.formDisabled = true;
  }
  handleDelete(entry: Entry) {
    if (confirm("Do you want to delete the entry from "+entry.departure+" to "+entry.arrival+"?")) {
      this.data.forEach((value, index) => {
        if (value.id == entry.id) this.data.splice(index, 1);
      })
    }
  }
  handleEdit(entry: Entry) {
    this.formDisabled = false;
    this.editedEntry.id = entry.id;
    this.editedEntry.departure = entry.departure;
    this.editedEntry.arrival = entry.arrival;
    this.editedEntry.fare = entry.fare;
  }
  editSubmitted() {
    if (confirm("Do you want to edit the fare of the route, from "+
      this.editedEntry.departure+" to "+this.editedEntry.arrival+" as "+this.editedEntry.fare+"?")) {
      this.data.forEach((value, index) => {
        if (value.id == this.editedEntry.id) {
          value.departure = this.editedEntry.departure;
          value.arrival = this.editedEntry.arrival;
          value.fare = this.editedEntry.fare;
        }
      })
    }
    this.handleEditClear();
    this.formDisabled = true;
  }
}
