import {Injectable, InjectionToken} from '@angular/core';
import {Route} from "../models/route";
import {INITIAL_ROUTES} from "../shared/routes";

@Injectable({
  providedIn: 'root'
})
export class RouteService {

  constructor() { }

  getRoutes(): Route[] {
    return INITIAL_ROUTES;
  }
}
