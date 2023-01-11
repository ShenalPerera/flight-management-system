import { Injectable, InjectionToken} from '@angular/core';
import {Route} from "../models/route";
import {INITIAL_ROUTES} from "../shared/routes";

@Injectable({
  providedIn: 'root'
})
export class RouteService {

  referringRoutes !: Route[];
  numberOfRoutes !: number
  constructor() { }

  getRoutes(): Route[] {
    this.referringRoutes = INITIAL_ROUTES;
    this.numberOfRoutes = INITIAL_ROUTES.length;
    return this.referringRoutes;
  }

  handleDuplicatesWhenCreating(departure: string, destination: string): boolean {
    let hasDuplicate: boolean = false;
    for (let route of this.referringRoutes) {
      if (route.departure === departure && route.destination === destination) {
        hasDuplicate = true;
        break;
      }
    }
    return hasDuplicate;
  }

  handleDuplicatesWhenUpdating(departure: string, destination: string, routeID: number): boolean {
    let hasDuplicate: boolean = false;
    for (let route of this.referringRoutes) {
      if ((route.departure === departure && route.destination === destination) && (route.routeID != routeID)) {
        hasDuplicate = true;
        break;
      }
    }
    return hasDuplicate;
  }

  updateRoute(data: Route) {
    this.referringRoutes.forEach((route)=>{
      if (route.routeID == data.routeID) {
        route.departure = data.departure.toLowerCase();
        route.destination = data.destination.toLowerCase();
        route.mileage = data.mileage;
        route.durationH = data.durationH;

      }
    });
  }

  createRoute(data: Route) {
    this.numberOfRoutes++;
    this.referringRoutes.push(
      {
        routeID: this.numberOfRoutes,
        departure: data.departure.toLowerCase(),
        destination: data.destination.toLowerCase(),
        mileage: data.mileage,
        durationH: data.durationH,
      }
    )
  }

  setDeparturesAndDestinations(departuresList: string[], destinationsList: string[]) {
    departuresList = [];
    destinationsList = [];
    this.referringRoutes.forEach((route)=>{
      if (!(departuresList.includes(route.departure))) {
        departuresList.push(route.departure);
      }
      if (!(destinationsList.includes(route.destination))) {
        destinationsList.push(route.destination);
      }
    });

    return {
      dpList: departuresList,
      dsList: destinationsList
    }
  }

  deleteRecord(routeID: number, currentRoutes: Route[]) {
    this.referringRoutes = this.referringRoutes.filter((route)=>{
      return route.routeID !== routeID;
    });

    return this.referringRoutes;
  }


}
