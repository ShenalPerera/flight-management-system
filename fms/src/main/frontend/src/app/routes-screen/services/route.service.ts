import {Injectable} from '@angular/core';
import {Route} from "../models/route";
import {HttpClient, HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class RouteService  {


  ALL_ROUTES: Route[];
  tempALL_ROUTES !: Route[];
  numberOfAllRoutes !: number
  constructor(private http: HttpClient) {
    this.ALL_ROUTES = [
      {routeID: 1, departure: "nepal", destination: "dubai", mileage: 1223.45, durationH: 12.5},
      {routeID: 2, departure: "nepal", destination: "sydney", mileage: 1433.43, durationH: 32.7},
      {routeID: 3, departure: "nepal", destination: "india", mileage: 1343.45, durationH: 2.4},
      {routeID: 4, departure: "nepal", destination: "england", mileage: 1223.32, durationH: 23.0},
      {routeID: 5, departure: "dubai", destination: "nepal", mileage: 12543.45, durationH: 13.9},
      {routeID: 6, departure: "dubai", destination: "sydney", mileage: 124.5, durationH: 1.2},
      {routeID: 7, departure: "dubai", destination: "india", mileage: 1443.45, durationH: 5.9},
      {routeID: 8, departure: "dubai", destination: "england", mileage: 223.45, durationH: 32.7},
      {routeID: 9, departure: "india", destination: "nepal", mileage: 1263.45, durationH: 24.8},
      {routeID: 10, departure: "india", destination: "dubai", mileage: 1923.45, durationH: 14.6},
      {routeID: 11, departure: "india", destination: "sydney", mileage: 2223.45, durationH: 15.5},
      {routeID: 12, departure: "india", destination: "england", mileage: 128.45, durationH: 5.4},
      {routeID: 13, departure: "sydney", destination: "india", mileage: 23.45, durationH: 17.2},
      {routeID: 14, departure: "sydney", destination: "england", mileage: 523.45, durationH: 15.9},
      {routeID: 15, departure: "sydney", destination: "sydney", mileage: 323.45, durationH: 9.4},
      {routeID: 16, departure: "sydney", destination: "nepal", mileage: 1288.45, durationH: 7.2},
      {routeID: 17, departure: "england", destination: "nepal", mileage: 723.45, durationH: 10.8},
      {routeID: 18, departure: "england", destination: "india", mileage: 1623.45, durationH: 54.8},
      {routeID: 19, departure: "england", destination: "england", mileage: 623.45, durationH: 5.2},
      {routeID: 20, departure: "england", destination: "dubai", mileage: 723.45, durationH: 2.8},
    ];

    this.numberOfAllRoutes = this.ALL_ROUTES.length;
  }

  getRoutesFromBackend() {
    return this.http.get<Route[]>('http://localhost:8080/api/routes-screen/get-routes');
  }

  initializeDeparturesAndDestinations(departuresList: string[], destinationsList: string[], routes: Route[]) {
    departuresList = [];
    destinationsList = [];

    routes.forEach((route)=>{
      if (!(departuresList.includes(route.departure))) {
        departuresList.push(route.departure);
      }
      if (!(destinationsList.includes(route.destination))) {
        destinationsList.push(route.destination);
      }
    });

    departuresList.sort();
    destinationsList.sort();
    return {
      dpList: departuresList,
      dsList: destinationsList
    }
  }

  createRouteInBackend(route: Route) {
    return this.http.post<any>('http://localhost:8080/api/routes-screen/create-route', route);
  }

  updateRouteInBackend(route: Route) {
    return this.http.put<any>('http://localhost:8080/api/routes-screen/update-route', route);
  }

  deleteRecordInBackend(routeID: number) {
    return this.http.delete('http://localhost:8080/api/routes-screen/delete-route',
      {params: new HttpParams().set('routeID', routeID)});
  }


  getRoutes(): Route[] {
    return this.ALL_ROUTES;
  }

  handleDuplicatesWhenCreating(departure: string, destination: string): boolean {
    let hasDuplicate: boolean = false;
    for (let route of this.ALL_ROUTES) {
      if (route.departure === departure && route.destination === destination) {
        hasDuplicate = true;
        break;
      }
    }
    return hasDuplicate;
  }


  handleDuplicatesWhenUpdating(departure: string, destination: string, routeID: number): boolean {
    let hasDuplicate: boolean = false;
    for (let route of this.ALL_ROUTES) {
      if ((route.departure === departure && route.destination === destination) && (route.routeID != routeID)) {
        hasDuplicate = true;
        break;
      }
    }
    return hasDuplicate;
  }

  updateRoute(data: Route) {
    this.ALL_ROUTES.forEach((route)=>{
      if (route.routeID == data.routeID) {
        route.departure = data.departure.toLowerCase();
        route.destination = data.destination.toLowerCase();
        route.mileage = data.mileage;
        route.durationH = data.durationH;

      }
    });
  }

  createRoute(data: Route) {
    this.numberOfAllRoutes++;
    this.ALL_ROUTES.push(
      {
        routeID: this.numberOfAllRoutes,
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

    this.ALL_ROUTES.forEach((route)=>{
      if (!(departuresList.includes(route.departure))) {
        departuresList.push(route.departure);
      }
      if (!(destinationsList.includes(route.destination))) {
        destinationsList.push(route.destination);
      }
    });

    departuresList.sort();
    destinationsList.sort();
    return {
      dpList: departuresList,
      dsList: destinationsList
    }
  }

  deleteRecord(routeID: number) {
    const routeIndex = this.ALL_ROUTES.findIndex((route :Route) => {
      return route.routeID === routeID;
    });

    if (routeIndex !== -1) {
      this.ALL_ROUTES.splice(routeIndex, 1);
    }
  }

  filterData(searchFormDeparture: string, searchFormDestination: string){
    return this.ALL_ROUTES.filter(
      x => (searchFormDeparture === "" || searchFormDeparture === x.departure)
        && (searchFormDestination === "" || searchFormDestination === x.destination))
  }

}
