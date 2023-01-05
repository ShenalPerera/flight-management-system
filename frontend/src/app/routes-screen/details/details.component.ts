// import {Component, EventEmitter, Inject, Output} from '@angular/core';
// import { ALL_ROUTES } from "../shared/routes";
//
// export interface Route {
//   routeID: number;
//   departure: string;
//   destination: string;
//   mileage: number;
//   durationH: number;
//   durationM: number;
// }
//
//
//
//
//
// const ROUTES_DATA: Route[] = ALL_ROUTES;
// @Component({
//   selector: 'app-details',
//   templateUrl: './details.component.html',
//   styleUrls: ['./details.component.scss']
// })
// export class DetailsComponent {
//   dataSource = ROUTES_DATA;
//   formData !: [number, string, string, number, number, number];
//   @Output() sendFormDataEvent = new EventEmitter<[number, string, string, number, number, number]>();
//
//   editRoute(route: any) {
//     console.log(route);
//   }
//
//   gotData(data: [number, string, string, number, number, number]) {
//     console.log(data);
//     this.formData = data;
//     this.sendFormDataEvent.emit(data);
//   }
//
//
//
// }
