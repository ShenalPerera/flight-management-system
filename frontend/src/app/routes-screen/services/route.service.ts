import {Injectable, InjectionToken} from '@angular/core';
import {Route} from "../models/route";
import {INITIAL_ROUTES} from "../shared/routes";
import {NgForm} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class RouteService {

  referringRoutes !: Route[];
  constructor() { }

  getRoutes(): Route[] {
    this.referringRoutes = INITIAL_ROUTES;
    return this.referringRoutes;
  }

  updateRoute(data: Route) {
    console.log("Came to update the route: "+data.routeID);
    this.referringRoutes.forEach((route)=>{
      if (route.routeID == data.routeID) {
        console.log('A record found');

        // route.departure = f.value['departure'];
        // route.destination = f.value['destination'];
        // route.mileage = f.value['mileage'];
        // route.durationH = f.value['durationH'];
        // route.durationM = f.value['durationM'];

        route.departure = data.departure;
        route.destination = data.destination;
        route.mileage = data.mileage;
        route.durationH = data.durationH;

        console.log("record updated");
      }
    });
  }

  createRoute(data: Route) {
    this.referringRoutes.push(
      {
        routeID: this.referringRoutes.length+1,
        departure: data.departure,
        destination: data.destination,
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

    console.log("Now inital is: "+this.referringRoutes);
    return {
      dpList: departuresList,
      dsList: destinationsList
    }
    // console.log('departure list '+departuresList);
  }

  deleteRecord(routeID: number, currentRoutes: Route[]) {
    this.referringRoutes = this.referringRoutes.filter((route)=>{
      return route.routeID !== routeID;
    });

    return this.referringRoutes;
    // return currentRoutes.filter((route)=>{
    //   return route.routeID !== routeID;
    // });
  }




}
