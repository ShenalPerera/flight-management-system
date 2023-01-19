import { Injectable } from '@angular/core';
import { Entry } from "../shared/entry.model";
import {HttpClient, HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class FareService {
  CONFIG_URL: string = "http://localhost:8080/api/fares"

  constructor(private http: HttpClient) { }

  getLocations() {
    return this.http.get<string[]>(this.CONFIG_URL+"/locations");
  }
  getAllEntries() {
    return this.http.get<Entry[]>(this.CONFIG_URL+"/search");
  }
  getFilteredEntries(departure: string, arrival: string) {
    return this.http.get<Entry[]>(this.CONFIG_URL + "/search",
      { params: new HttpParams().set('departure', departure).set('arrival', arrival) });
  }
  deleteEntry(id: number) {
    return this.http.delete<number>(this.CONFIG_URL+"/entry",
      { params: new HttpParams().set('id', id) });
  }
  editEntry(entry : Entry) {
    return this.http.put<Entry>(this.CONFIG_URL+"/entry", entry);
  }
  createEntry(entry: Entry) {
    return this.http.post<Entry>(this.CONFIG_URL+"/entry", entry);
  }
}
