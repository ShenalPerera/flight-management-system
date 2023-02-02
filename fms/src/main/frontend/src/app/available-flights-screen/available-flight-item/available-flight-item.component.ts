import { Component } from '@angular/core';
import {AvailableFlightModel} from "../models/available-flight.model";

@Component({
  selector: 'tr [app-available-flight-item]',
  templateUrl: './available-flight-item.component.html',
  styleUrls: ['./available-flight-item.component.scss']
})
export class AvailableFlightItemComponent {
  availableFlight !: AvailableFlightModel;

  constructor() {
  }


}
