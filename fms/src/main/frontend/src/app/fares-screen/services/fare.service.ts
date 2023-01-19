import { Injectable } from '@angular/core';
import { Entry } from "../shared/entry.model";
import {HttpClient, HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class FareService {
  data: Entry[] = [
    {id:1, departure:"colombo", arrival:"dubai", fare:50},
    {id:2, departure:"colombo", arrival:"sydney", fare:75},
    {id:3, departure:"dubai", arrival:"colombo", fare:50},
    {id:4, departure:"colombo", arrival:"new york", fare:150},
    {id:5, departure:"new york", arrival:"sydney", fare:225},
    {id:6, departure:"new york", arrival:"colombo", fare:150},
    {id:7, departure:"london", arrival:"colombo", fare:125},
    {id:8, departure:"dubai", arrival:"london", fare:80},
    {id:9, departure:"paris", arrival:"sydney", fare:185},
    {id:10, departure:"new york", arrival:"paris", fare:135},
  ];
  currentId: number = this.data.length;
  configUrl: string = "http://localhost:8080/api/fares"

  constructor(private http: HttpClient) { }

  getLocations() {
    return this.http.get<string[]>(this.configUrl+"/locations");
  }
  getEntries() {
    return this.http.get<Entry[]>(this.configUrl+"/search");
  }

  // generateDepartingLocations(): string[] {
  //   return [...new Set(this.data.map(item => item.departure))];
  // }
  // generateArrivingLocations(): string[] {
  //   return [...new Set(this.data.map(item => item.departure))];
  // }

  filterDataService(departure: string, arrival: string) {
    return this.http.get<Entry[]>(this.configUrl + "/search",
      { params: new HttpParams().set('departure', departure).set('arrival', arrival) });
  }
  deleteEntry(id: number): void {
    this.data.forEach((value, index) => {
      if (value.id == id)
        this.data.splice(index, 1);
    })
  }

  // isDuplicate(departure: string, arrival: string): number {
  //   let duplicateId = 0;
  //   this.data.forEach((value) => {
  //     if ((value.departure === departure) && (value.arrival === arrival))
  //       duplicateId = value.id;
  //       return;
  //   })
  //   return duplicateId;
  // }

  editEntry(entry : Entry) {
    return this.http.put<Entry>(this.configUrl+"/entry", entry);
    // this.data.forEach((value) => {
    //   if (value.id == entry.id) {
    //     value.departure = entry.departure;
    //     value.arrival = entry.arrival;
    //     value.fare = entry.fare;
    //   }
    // })
  }
  createEntry(entry: Entry) {
    return this.http.post<Entry>(this.configUrl+"/entry", entry);
    // this.data.push({
    //   id: ++this.currentId,
    //   departure: entry.departure,
    //   arrival: entry.arrival,
    //   fare: entry.fare
    // });
  }
}
