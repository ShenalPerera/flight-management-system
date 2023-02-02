import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Fare } from '../shared/entry.model'

@Component({
  selector: 'tr [app-fare-item]',
  templateUrl: './fare-item.component.html',
  styleUrls: ['./fare-item.component.scss']
})
export class FareItemComponent {
  @Input() entry!: Fare;
  @Input() rowNumber!: number;
  @Output() editButtonClicked: EventEmitter<Fare> = new EventEmitter<Fare>();
  @Output() deleteButtonClicked: EventEmitter<Fare> = new EventEmitter<Fare>();
  editEntry() {
    this.editButtonClicked.emit(this.entry);
  }
  deleteEntry() {
    this.deleteButtonClicked.emit(this.entry);
  }
}
