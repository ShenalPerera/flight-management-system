import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FaresScreenComponent } from "./fares-screen/fares-screen.component";
import { RoutesScreenComponent } from "./routes-screen/routes-screen.component";
import { FlightsComponent } from "./flights/flights.component";

const routes: Routes = [
  { path: "", component: FaresScreenComponent },
  { path: "routes", component: RoutesScreenComponent },
  { path: "flights", component: FlightsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
