import {Component, Inject, Input, OnChanges, SimpleChanges} from '@angular/core';
import {Route} from "../models/route";
import {RouteService} from "../services/route.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-create-form',
  templateUrl: './create-form.component.html',
  styleUrls: ['./create-form.component.scss']
})
export class CreateFormComponent implements OnChanges {


  // @Input() formData: [number, string, string, number, number, number] = [0, '', '', 0, 0, 0];
  @Input() formData: [number, string, string, number, number, number] = [NaN, '', '', NaN, NaN, NaN];
  @Input() allRoutes !: Route[];

  routeID!: number;
  departure!: string;
  destination!: string;
  mileage!: number;
  durationH!: number;
  durationM!: number;

  @Input() operationType !: string;

  onSubmitCreate() {
    // if (this.operationType == 'Apply') {
    this.routeService.createRoute(this.data);
    this.onNoClick();
    // }else if(this.operationType == 'Create') {
    // this.routeService.createRoute(f);
    // }
    // this.routeService.updateRoute(this.routeID, f);



    // console.log(f.value);  // { first: '', last: '' }
    // console.log(f.value['departure']);  // { first: '', last: '' }
    // const found = this.allRoutes.find((obj)=>{
    //   if (this.routeID == obj.routeID) {
    //     // console.log("has to be changed from "+obj.departure+" to "+f.value['departure']);
    //     obj.departure = f.value['departure'];
    //     obj.destination = f.value['destination'];
    //     obj.mileage = f.value['mileage'];
    //     obj.durationH = f.value['durationH'];
    //     obj.durationM = f.value['durationM'];
    //   }
    // })
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  constructor(private routeService: RouteService,
              public dialogRef: MatDialogRef<CreateFormComponent>,
              @Inject(MAT_DIALOG_DATA) public data: Route) {
    this.formData = [NaN, '', '', NaN, NaN, NaN];
  }

  clearInputs() {
    this.departure = '';
    this.destination = '';
    this.mileage = NaN;
    this.durationH = NaN;
    this.durationM = NaN;
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
    } else {
      this.departure = '';
      this.destination = '';
      this.mileage = NaN;
      this.durationH = NaN;
      this.durationM = NaN;
    }

  }

}
