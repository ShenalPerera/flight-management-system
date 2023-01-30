import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {of, Subject} from "rxjs";

@Injectable({
  providedIn:'root'
})

export class AirportsHandleService{
  private airportsList !: string[];
  airportsListChange= new Subject<string[]>;
  constructor(private http:HttpClient) {}

  getAirportsList(){
    let airports = localStorage.getItem('airports');
    if (!airports){
      return this.http.get<string[]>("http://localhost:8080/flights/get-airports");
    }
    else{
      this.airportsList = airports ? JSON.parse(airports):[];
      return of(this.airportsList);
    }
  }

}
