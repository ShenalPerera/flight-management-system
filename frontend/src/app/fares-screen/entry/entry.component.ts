import { Component, Input } from '@angular/core';

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
  editEntry() {}
  deleteEntry() {}
}
