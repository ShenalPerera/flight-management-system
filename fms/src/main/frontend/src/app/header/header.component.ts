import { Component } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  flightsActive: boolean = false;
  routesActive: boolean = false;
  faresActive: boolean = false;
  searchActive: boolean = true;
  activateFlights() {
    this.flightsActive = true;
    this.routesActive = false;
    this.faresActive = false;
    this.searchActive = false;
  }
  activateRoutes() {
    this.flightsActive = false;
    this.routesActive = true;
    this.faresActive = false;
    this.searchActive = false;
  }
  activateFares() {
    this.flightsActive = false;
    this.routesActive = false;
    this.faresActive = true;
    this.searchActive = false;
  }
  activateSearch() {
    this.flightsActive = false;
    this.routesActive = false;
    this.faresActive = false;
    this.searchActive = true;
  }
}
