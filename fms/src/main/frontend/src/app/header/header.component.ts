import { Component } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  flightsActive: boolean = this.getPath() === 'flights';
  routesActive: boolean = this.getPath() === 'routes';
  faresActive: boolean = this.getPath() === 'fares';
  searchActive: boolean = this.getPath() === '';
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
  getPath() {
    return window.location.href.split('/').pop();
  }
}
