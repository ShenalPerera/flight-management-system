import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Entry } from '../fares-screen.component'

@Component({
  selector: 'app-entry',
  templateUrl: './entry.component.html',
  styleUrls: ['./entry.component.scss']
})
export class EntryComponent {
  @Input() id!: number;
  @Input() departure!: string;
  @Input() arrival!: string;
  @Input() fare!: number;
  @Output() editButtonClicked = new EventEmitter<Entry>();
  editEntry() {
    this.editButtonClicked.emit(this);
  }
  @Output() deleteButtonClicked = new EventEmitter<Entry>();
  deleteEntry() {
    this.deleteButtonClicked.emit(this);
  }
}
