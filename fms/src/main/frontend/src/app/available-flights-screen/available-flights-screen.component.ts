import {Component, OnInit} from '@angular/core';
import {AvailableFlightModel} from "./models/available-flight.model";

@Component({
  selector: 'app-available-flights-screen',
  templateUrl: './available-flights-screen.component.html',
  styleUrls: ['./available-flights-screen.component.scss']
})
export class AvailableFlightsScreenComponent implements OnInit{
  availableFlightList !: AvailableFlightModel[];

  ngOnInit() {

  }
}
