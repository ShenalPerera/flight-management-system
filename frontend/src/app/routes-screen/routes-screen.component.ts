import { Component } from '@angular/core';


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
  selector: 'app-routes-screen',
  templateUrl: './routes-screen.component.html',
  styleUrls: ['./routes-screen.component.scss']
})
export class RoutesScreenComponent {


  displayedColumns: string[] = ['routeID', 'departure', 'destination', 'mileage', 'durationH', 'durationM'];
  dataSource = ROUTES_DATA;

  // routes = [
  //   {
  //     routeID: 1,
  //     departure: "Sri Lanka",
  //     destination: "Dubai",
  //     mileage: 1223.45,
  //     durationH: 12,
  //     durationM: 30
  //   },
  //   {
  //     routeID: 1,
  //     departure: "Sri Lanka",
  //     destination: "Dubai",
  //     mileage: 1223.45,
  //     durationH: 12,
  //     durationM: 30
  //   },
  //   {
  //     routeID: 1,
  //     departure: "Sri Lanka",
  //     destination: "Dubai",
  //     mileage: 1223.45,
  //     durationH: 12,
  //     durationM: 30
  //   },
  //   {
  //     routeID: 1,
  //     departure: "Sri Lanka",
  //     destination: "Dubai",
  //     mileage: 1223.45,
  //     durationH: 12,
  //     durationM: 30
  //   },
  //   {
  //     routeID: 1,
  //     departure: "Sri Lanka",
  //     destination: "Dubai",
  //     mileage: 1223.45,
  //     durationH: 12,
  //     durationM: 30
  //   }
  // ]

}
