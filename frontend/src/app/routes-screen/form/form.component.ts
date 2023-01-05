import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {NgForm} from "@angular/forms";

import {ALL_ROUTES} from "../shared/routes";

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnChanges{

  // @Input() formData: [number, string, string, number, number, number] = [0, '', '', 0, 0, 0];
  @Input() formData: [number, string, string, number, number, number] = [NaN, '', '', NaN, NaN, NaN];

  routeID!: number;
  departure!: string;
  destination!: string;
  mileage!: number;
  durationH!: number;
  durationM!: number;

  onSubmit(f: NgForm) {
    console.log(f.value);  // { first: '', last: '' }
    console.log(f.value['departure']);  // { first: '', last: '' }
    const found = ALL_ROUTES.find((obj)=>{
      if (this.routeID == obj.routeID) {
        // console.log("has to be changed from "+obj.departure+" to "+f.value['departure']);
        obj.departure = f.value['departure'];
      }
    })
  }

  constructor() {
    this.formData = [NaN, '', '', NaN, NaN, NaN];
  }



  ngOnChanges(changes: SimpleChanges) {
    console.log(changes);
    if (this.formData != undefined) {
      this.routeID = this.formData[0];
      this.departure = this.formData[1];
      this.destination = this.formData[2];
      this.mileage = this.formData[3];
      this.durationH = this.formData[4];
      this.durationM = this.formData[5];
    }

  }

}
