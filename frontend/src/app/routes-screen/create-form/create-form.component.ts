import {Component, Inject, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Route} from "../models/route";
import {RouteService} from "../services/route.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {numberCheckValidator} from "../shared/validations";

@Component({
  selector: 'app-create-form',
  templateUrl: './create-form.component.html',
  styleUrls: ['./create-form.component.scss']
})
export class CreateFormComponent implements OnInit {


  // @Input() formData: [number, string, string, number, number, number] = [0, '', '', 0, 0, 0];
  @Input() formData: [number, string, string, number, number] = [NaN, '', '', NaN, NaN];
  @Input() allRoutes !: Route[];

  routeID!: number;
  departure!: string;
  destination!: string;
  mileage!: number;
  durationH!: number;
  durationM!: number;

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



  createdRoute !: Route;
  onSubmitCreate() {
    // if (this.operationType == 'Apply') {
    this.createdRoute = {
      routeID: this.data.routeID,
      departure: this.sampleForm.value['departure'],
      destination: this.sampleForm.value['destination'],
      mileage: this.sampleForm.value['mileage'],
      durationH: this.sampleForm.value['durationH'],
    };
    this.routeService.createRoute(this.createdRoute);
    this.onNoClickWithoutConfirmation();
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

  areSameValues(): boolean {
    if (
      this.sampleForm.value['departure'] == this.data.departure &&
      this.sampleForm.value['destination'] == this.data.destination &&
      this.sampleForm.value['mileage'] == this.data.mileage &&
      this.sampleForm.value['durationH'] == this.data.durationH
    ) {
      return true;
    }else {
      return false;
    }
  }

  onNoClick(): void {
    if (this.areSameValues()) {
      this.dialogRef.close();
    }else {
      if(confirm('Are you sure ?')) {
        this.dialogRef.close();
      }
    }


  }

  onNoClickWithoutConfirmation(): void {
    this.dialogRef.close();
  }

  constructor(private routeService: RouteService,
              public dialogRef: MatDialogRef<CreateFormComponent>,
              @Inject(MAT_DIALOG_DATA) public data: Route) {
    this.formData = [NaN, '', '', NaN, NaN];
  }

  clearInputs() {
    this.departure = '';
    this.destination = '';
    this.mileage = NaN;
    this.durationH = NaN;
  }





}
