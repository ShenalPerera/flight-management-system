import {Injectable} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http";
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
    return this.http.post<Flight>("http://localhost:8080/flights/add-flight",flight,{observe:'response'});
  }

  removeFlight(id:number){
    return this.http.delete("http://localhost:8080/flights/delete-flight",{params: new HttpParams().set("id",id),observe:'response'});
  }

  editFlight(editedFlight:Flight){
    return this.http.put<Flight>("http://localhost:8080/flights/edit-flight",editedFlight,{observe:'response'});
  }

  searchFlight(flightData:{fNumber: string, fArrival: string, fDeparture: string, fArrivalDate: string, fDepartureDate: string ,fArrivalTime:string, fDepartureTime:string }){
    let searchParams = new HttpParams();
    searchParams = searchParams.append("flightNumber",flightData.fNumber);
    searchParams = searchParams.append("arrival",flightData.fArrival);
    searchParams = searchParams.append("departure",flightData.fDeparture);
    searchParams = searchParams.append("departureDate",flightData.fDepartureDate);
    searchParams = searchParams.append("arrivalDate",flightData.fArrivalDate);
    searchParams = searchParams.append("departureTime",flightData.fDepartureTime);
    searchParams = searchParams.append("arrivalTime",flightData.fArrivalTime);

    return this.http.get<Flight[]>("http://localhost:8080/flights/get-filtered-flights",{params: searchParams});
  }

}
