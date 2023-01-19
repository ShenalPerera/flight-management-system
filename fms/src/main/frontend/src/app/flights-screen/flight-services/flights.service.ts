import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Flight} from "../flight.model";

@Injectable({
  providedIn:'root'
})

export class FlightsService{

  constructor(private http:HttpClient) {}
  // Get
  fetchFlights(){
    return this.http.get<Flight[]>("http://localhost:8080/flights/get-flights");
  }

  addNewFlight(flight:Flight){
    return this.http.post<Flight>("http://localhost:8080/flights/add-flight",flight);
  }

}
