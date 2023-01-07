import {Component, Inject} from '@angular/core';
import {Route} from './models/route';


@Component({
  selector: 'app-routes-screen',
  templateUrl: './routes-screen.component.html',
  styleUrls: ['./routes-screen.component.scss']
})
export class RoutesScreenComponent {
  ALL_ROUTES: Route[] = [
    {routeID: 1, departure: "Sri Lanka", destination: "Dubai", mileage: 1223.45, durationH: 12, durationM: 30},
    {routeID: 2, departure: "Sri Lanka", destination: "Dubai", mileage: 1223.45, durationH: 12, durationM: 30},
    {routeID: 3, departure: "Sri Lanka", destination: "Dubai", mileage: 1223.45, durationH: 12, durationM: 30},
    {routeID: 4, departure: "Sri Lanka", destination: "New York", mileage: 1223.45, durationH: 12, durationM: 20}
  ];




  formDataInRouteScreen !: [number, string, string, number, number, number];

  // getFormData(data: [number, string, string, number, number, number]) {
  //   console.log('screen received: ' + data);
  //   this.formDataInRouteScreen = data;
  // }

  gotData(data: [number, string, string, number, number, number]) {
    console.log(data);
    this.formDataInRouteScreen = data;
  }

  deleteRecord(data: number) {
    this.ALL_ROUTES = this.ALL_ROUTES.filter((route)=>{
      return route.routeID !== data;
    });
    console.log('deleted with the id: '+data);
  }
}
