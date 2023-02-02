import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Flight} from "../flight.model";

@Component({
  selector: 'tr [app-flight-item]',
  templateUrl: './flight-item.component.html',
  styleUrls: ['./flight-item.component.scss']
})
export class FlightItemComponent implements OnInit{
  @Input() flight !: Flight;

  // event emitters
  @Output() flightDeleted = new EventEmitter<number>();
  @Output() flightEdited = new EventEmitter<Flight>();

  constructor() {
  }

  ngOnInit() {}

  onClickDelete(){
    if (confirm(`Do you want to delete this flight?\n
    Flight number: ${this.flight.flightNumber}\n
    Departure      : ${this.flight.departure}\n
    On ${this.flight.departureDate}   at  ${this.flight.departureTime}
      ` )){
      this.flightDeleted.emit(this.flight.flightId);
    }

  }

  onClickEdit(){
    this.flightEdited.emit(this.flight);
  }
}
