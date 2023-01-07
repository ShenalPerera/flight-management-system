import { Component, Input, Output, EventEmitter } from '@angular/core';

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
  @Output() entryDeleted = new EventEmitter<number>();
  editEntry() {}
  deleteEntry() {
    this.entryDeleted.emit(this.id);
  }
}
