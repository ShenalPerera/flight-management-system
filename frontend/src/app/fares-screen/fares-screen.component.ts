import {Component} from '@angular/core';

interface Entry {
  id: number;
  departure: string;
  arrival: string;
}
interface Locations {
  value: number;
  name: string;
}

@Component({
  selector: 'app-fares-screen',
  templateUrl: './fares-screen.component.html',
  styleUrls: ['./fares-screen.component.scss']
})

export class FaresScreenComponent {

  searchCriteria = {departure: "", arrival: ""};

  locations: Locations[] = [
    { value: 1, name: 'Colombo' },
    { value: 2, name: 'Dubai' },
    { value: 3, name: 'Sydney' },
  ];
  data: Entry[] = [
    {id:1, departure:"Colombo", arrival:"Dubai"},
    {id:2, departure:"Colombo", arrival:"Sydney"},
    {id:3, departure:"Dubai", arrival:"Colombo"},
  ];
  searchedData: Entry[] = this.data;
  filterData(){
    this.searchedData = this.data.filter(
      x => (this.searchCriteria.departure === "" || this.searchCriteria.departure === x.departure)
        && (this.searchCriteria.arrival === "" || this.searchCriteria.arrival === x.arrival))
  }
  handleClear() {
    this.searchCriteria.departure = "";
    this.searchCriteria.arrival = "";
    this.filterData();
  }
}
