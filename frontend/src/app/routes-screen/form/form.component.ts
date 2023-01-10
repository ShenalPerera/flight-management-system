import {Component, Inject, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {FormControl, FormGroup, NgForm, Validators} from "@angular/forms";

import { Route } from '../models/route';
import {RouteService} from "../services/route.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {numberCheckValidator} from "../shared/validations";

// import {ALL_ROUTES} from "../shared/routes";

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit{

  // @Input() formData: [number, string, string, number, number, number] = [0, '', '', 0, 0, 0];
  @Input() formData: [number, string, string, number, number] = [NaN, '', '', NaN, NaN];
  @Input() allRoutes !: Route[];

  routeID!: number;
  departure!: string;
  destination!: string;
  mileage!: number;
  durationH!: number;


  @Input() operationType !: string;

  sampleForm !: FormGroup;
  ngOnInit() {
    this.sampleForm = new FormGroup({
      'departure': new FormControl(this.data.departure, [Validators.required]),
      'destination': new FormControl(this.data.destination, [Validators.required]),
      'mileage': new FormControl(this.data.mileage, [Validators.required, numberCheckValidator()]),
      'durationH': new FormControl(this.data.durationH, [Validators.required, numberCheckValidator()]),
    });
  }

  updatedRoute !: Route;

    onSubmitUpdate() {
      this.updatedRoute = {
        routeID: this.data.routeID,
        departure: this.sampleForm.value['departure'],
        destination: this.sampleForm.value['destination'],
        mileage: this.sampleForm.value['mileage'],
        durationH: this.sampleForm.value['durationH'],
      };
      // console.log(this.sampleForm.value['departure']);
    // if (this.operationType == 'Apply') {
      this.routeService.updateRoute(this.updatedRoute);
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
              public dialogRef: MatDialogRef<FormComponent>,
              @Inject(MAT_DIALOG_DATA) public data: Route) {
    this.formData = [NaN, '', '', NaN, NaN];
  }

  clearInputs() {
    this.departure = '';
    this.destination = '';
    this.mileage = NaN;
    this.durationH = NaN;
  }



  // ngOnChanges(changes: SimpleChanges) {
  //   console.log(changes);
  //   if (this.formData != undefined) {
  //     this.routeID = this.formData[0];
  //     this.departure = this.formData[1];
  //     this.destination = this.formData[2];
  //     this.mileage = this.formData[3];
  //     this.durationH = this.formData[4];
  //     this.durationM = this.formData[5];
  //   } else {
  //     this.departure = '';
  //     this.destination = '';
  //     this.mileage = NaN;
  //     this.durationH = NaN;
  //     this.durationM = NaN;
  //   }
  //
  // }

}
