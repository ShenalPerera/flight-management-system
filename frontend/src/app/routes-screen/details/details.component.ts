import {Component, Inject} from '@angular/core';


export interface Route {
  routeID: number;
  departure: string;
  destination: string;
  mileage: number;
  durationH: number;
  durationM: number;
}





const ROUTES_DATA: Route[] = [
  {routeID: 1, departure: "Sri Lanka", destination: "Dubai", mileage: 1223.45, durationH: 12, durationM: 30},
  {routeID: 2, departure: "Sri Lanka", destination: "Dubai", mileage: 1223.45, durationH: 12, durationM: 30},
  {routeID: 3, departure: "Sri Lanka", destination: "Dubai", mileage: 1223.45, durationH: 12, durationM: 30},
  {routeID: 4, departure: "Sri Lanka", destination: "New York", mileage: 1223.45, durationH: 12, durationM: 20}
];
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
