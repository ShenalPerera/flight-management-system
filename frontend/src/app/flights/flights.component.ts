import {Component, OnInit} from '@angular/core';
import {Flight} from "./flight.model";
import {DataService} from "../../assets/data-service";

@Component({
  selector: 'app-flights',
  templateUrl: './flights.component.html',
  styleUrls: ['./flights.component.scss'],
  providers:[DataService]
})


export class FlightsComponent implements OnInit{

  public  flights !: Flight[];

  constructor(private dataService : DataService) {

  }

  ngOnInit() {
    this.flights = this.dataService.getFlights();

  }

  deleteFlight(flight : Flight){
    this.dataService.removeFlight(flight);
  }


}
