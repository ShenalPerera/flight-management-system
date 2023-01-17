import { Component } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  flightsActive: boolean = true;
  routesActive: boolean = false;
  faresActive: boolean = false;
  activateFlights() {
    this.flightsActive = true;
    this.routesActive = false;
    this.faresActive = false;
  }
  activateRoutes() {
    this.flightsActive = false;
    this.routesActive = true;
    this.faresActive = false;
  }
  activateFares() {
    this.flightsActive = false;
    this.routesActive = false;
    this.faresActive = true;
  }
}
