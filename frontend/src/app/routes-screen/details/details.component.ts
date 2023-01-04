import {Component, Inject} from '@angular/core';
import { ALL_ROUTES } from "../shared/routes";

export interface Route {
  routeID: number;
  departure: string;
  destination: string;
  mileage: number;
  durationH: number;
  durationM: number;
}





const ROUTES_DATA: Route[] = ALL_ROUTES;
@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.scss']
})
export class DetailsComponent {
  clickedRows = new Set<Route>();

  selectedRoute: string = ''

  selectedItem: any;

  changedClickedRow(row: Route) {
    console.log(row.destination + '   ' + row.departure);
    this.selectedItem = String(row.destination) + '   ' + String(row.departure);
  }



  displayedColumns: string[] = ['routeID', 'departure', 'destination', 'mileage', 'durationH', 'durationM', 'action'];
  dataSource = ROUTES_DATA;



}
