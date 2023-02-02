import {Injectable} from "@angular/core";
import {HttpClient, HttpParams} from "@angular/common/http";
import {AvailableFlightModel} from "../models/available-flight.model";

@Injectable({
  providedIn:'root'
})

export class AvailableFlightsService{

    constructor(private http:HttpClient) {
    }

    getAvailableFlights(searchCriteria:{flightNumber:string,departure:string,arrival:string,departureStartDate:string,departureEndDate:string}){
      let searchParams = new HttpParams();
      searchParams = searchParams.append("flightNumber",searchCriteria.flightNumber);
      searchParams = searchParams.append("departure",searchCriteria.departure);
      searchParams = searchParams.append("arrival",searchCriteria.arrival);
      searchParams = searchParams.append("departureStartDate",searchCriteria.departureStartDate);
      searchParams = searchParams.append("departureEndDate",searchCriteria.departureEndDate);

      return  this.http.get<AvailableFlightModel[]>('http://localhost:8080/api/availability',{params:searchParams})
    }
}
