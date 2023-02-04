import {Injectable} from '@angular/core';
import {Route} from "../models/route";
import {HttpClient, HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class RouteService  {
  constructor(private http: HttpClient) {
  }

  getRoutes() {
    return this.http.get<Route[]>('http://localhost:8080/api/routes-screen/get-routes');
  }

  getOneRoute(routeID: number) {
    return this.http.get<Route>('http://localhost:8080/api/routes-screen/get-one-route',
      {params: new HttpParams().set('routeID', routeID), observe: 'response'});
  }

  createRoute(route: Route) {
    // route.created = new Date();
    return this.http.post<any>('http://localhost:8080/api/routes-screen/create-route', route, {observe: 'response'});
  }

  updateRoute(route: Route) {
    // route.modified = new Date();
    return this.http.put<any>('http://localhost:8080/api/routes-screen/update-route', route, {observe: 'response'});
  }

  deleteRecord(routeID: number) {
    return this.http.delete('http://localhost:8080/api/routes-screen/delete-route',
      {params: new HttpParams().set('routeID', routeID), observe: 'response'});
  }

  filterRoutes(departure: string, destination: string) {
    return this.http.get<Route[]>('http://localhost:8080/api/routes-screen/search-routes',
      {
        params: new HttpParams().set('departure', departure).set('destination', destination)
      })
  }


}
