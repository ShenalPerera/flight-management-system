import {Injectable, InjectionToken} from '@angular/core';
import {Route} from "../models/route";
import {INITIAL_ROUTES} from "../shared/routes";
import {NgForm} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class RouteService {

  constructor() { }

  getRoutes(): Route[] {
    return INITIAL_ROUTES;
  }

  updateRoute(routeID: number, f: NgForm) {
    INITIAL_ROUTES.forEach((route)=>{
      if (route.routeID == routeID) {
        route.departure = f.value['departure'];
        route.destination = f.value['destination'];
        route.mileage = f.value['mileage'];
        route.durationH = f.value['durationH'];
        route.durationM = f.value['durationM'];

        console.log("record updated");
      }
    });
  }
}
