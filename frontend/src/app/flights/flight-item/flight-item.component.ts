import {Component, Input, OnInit} from '@angular/core';
import {Flight} from "../flight.model";

@Component({
  selector: 'tr [app-flight-item]',
  templateUrl: './flight-item.component.html',
  styleUrls: ['./flight-item.component.scss']
})
export class FlightItemComponent implements OnInit{
  @Input() flight !: Flight;
  constructor() {
  }

  ngOnInit() {}
}
