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
  dataSource = ROUTES_DATA;

  editRoute(route: any) {
    console.log(route);
  }

  gotData() {
    console.log("Data got !!");
  }



}
