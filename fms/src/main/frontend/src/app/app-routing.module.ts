import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FaresScreenComponent } from "./fares-screen/fares-screen.component";
import { RoutesScreenComponent } from "./routes-screen/routes-screen.component";
import { FlightsScreenComponent } from "./flights-screen/flights-screen.component";

const routes: Routes = [
  { path: "fares", component: FaresScreenComponent },
  { path: "routes", component: RoutesScreenComponent },
  { path: "", component: FlightsScreenComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
