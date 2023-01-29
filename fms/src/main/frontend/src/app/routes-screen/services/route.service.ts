import {Injectable} from '@angular/core';
import {Route} from "../models/route";
import {HttpClient, HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class RouteService  {
  constructor(private http: HttpClient) {
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
    // route.created = new Date();
    return this.http.post<any>('http://localhost:8080/api/routes-screen/create-route', route, {observe: 'response'});
  }

  updateRouteInBackend(route: Route) {
    // route.modified = new Date();
    return this.http.put<any>('http://localhost:8080/api/routes-screen/update-route', route, {observe: 'response'});
  }

  deleteRecordInBackend(routeID: number) {
    return this.http.delete('http://localhost:8080/api/routes-screen/delete-route',
      {params: new HttpParams().set('routeID', routeID)});
  }

  fetchSpecificRoutesFromBackend(departure: string, destination: string) {

    return this.http.get<Route[]>('http://localhost:8080/api/routes-screen/search-routes',
      {
        params: new HttpParams().set('departure', departure).set('destination', destination)
      })
  }


}
