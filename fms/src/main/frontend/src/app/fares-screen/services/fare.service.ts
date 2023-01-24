import { Injectable } from '@angular/core';
import { Entry } from "../shared/entry.model";
import {HttpClient, HttpParams, HttpResponse} from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class FareService {
  CONFIG_URL: string = "http://localhost:8080/api/fares"

  constructor(private http: HttpClient) { }

  getLocations(): Observable<string[]> {
    return this.http.get<string[]>(this.CONFIG_URL+"/locations");
  }
  getAllEntries(): Observable<Entry[]> {
    return this.http.get<Entry[]>(this.CONFIG_URL+"/search");
  }
  getFilteredEntries(departure: string, arrival: string): Observable<Entry[]> {
    return this.http.get<Entry[]>(this.CONFIG_URL + "/search",
      { params: new HttpParams().set('departure', departure).set('arrival', arrival) });
  }
  deleteEntry(id: number): Observable<number> {
    return this.http.delete<number>(this.CONFIG_URL+"/fare",
      { params: new HttpParams().set('id', id) });
  }
  editEntry(entry : Entry): Observable<HttpResponse<Entry>> {
    return this.http.put<Entry>(this.CONFIG_URL+"/fare", entry, { observe: "response" });
  }
  createEntry(entry: Entry): Observable<HttpResponse<Entry>> {
    return this.http.post<Entry>(this.CONFIG_URL+"/fare", entry, { observe: "response" });
  }
}
