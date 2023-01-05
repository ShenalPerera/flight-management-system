import { Component } from '@angular/core';
import {Flight} from "./flight.model";

@Component({
  selector: 'app-flights',
  templateUrl: './flights.component.html',
  styleUrls: ['./flights.component.scss']
})
export class FlightsComponent {
  public  flights : Flight[] = [

    new Flight(1234,"Colobmofgfgfgfgfgffffffffffffffffffffff","Canada",new Date(), new Date()),
    new Flight(1234,"Colobmo","Canada",new Date(), new Date())
  ];


  constructor() {
  }


}
