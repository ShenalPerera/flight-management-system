import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Entry } from '../Entry'

@Component({
  selector: 'tr [app-fare-item]',
  templateUrl: './fare-item.component.html',
  styleUrls: ['./fare-item.component.scss']
})
export class FareItemComponent {
  @Input() entry!: Entry;
  @Output() editButtonClicked: EventEmitter<Entry> = new EventEmitter<Entry>();
  @Output() deleteButtonClicked: EventEmitter<Entry> = new EventEmitter<Entry>();
  editEntry() {
    this.editButtonClicked.emit(this.entry);
  }
  deleteEntry() {
    this.deleteButtonClicked.emit(this.entry);
  }
}
