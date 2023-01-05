import {Component, EventEmitter, Input, Output} from '@angular/core';
// import {ALL_ROUTES} from "../shared/routes";

@Component({
  selector: 'app-entry',
  templateUrl: './entry.component.html',
  styleUrls: ['./entry.component.scss']
})
export class EntryComponent {

  @Input() routeID !: number;
  @Input() departure !: string;
  @Input() destination !: string;
  @Input() mileage !: number;
  @Input() durationH !: number;
  @Input() durationM !: number;

  displayEntry() {
    console.log("You clicked "+this.routeID+' '+this.departure);
  }

  deleteItem() {
    console.log("Do deletion");
  }

  @Output() sendItemsEvent = new EventEmitter<[number, string, string, number, number, number]>();

  sendItemsToParent() {
    this.sendItemsEvent.emit([this.routeID, this.departure, this.destination, this.mileage, this.durationH, this.durationM]);
    console.log("items sent to parent");
  }

}
