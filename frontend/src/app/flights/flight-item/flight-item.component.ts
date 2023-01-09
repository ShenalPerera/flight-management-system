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
    this.flightDeleted.emit(this.flight.id);
  }

  onClickEdit(){
    this.flightEdited.emit(this.flight);
  }
}
