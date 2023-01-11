import {Component, Inject, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {FormControl, FormGroup, NgForm, Validators} from "@angular/forms";

import { Route } from '../models/route';
import {RouteService} from "../services/route.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {numberCheckValidator} from "../shared/validations";



@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit{

  // @Input() formData: [number, string, string, number, number] = [NaN, '', '', NaN, NaN];
  // @Input() allRoutes !: Route[];

  routeID!: number;
  departure!: string;
  destination!: string;
  mileage!: number;
  durationH!: number;


  // @Input() operationType !: string;

  sampleForm !: FormGroup;
  ngOnInit() {
    this.sampleForm = new FormGroup({
      'departure': new FormControl(this.data.route.departure, [Validators.required]),
      'destination': new FormControl(this.data.route.destination, [Validators.required]),
      'mileage': new FormControl(this.data.route.mileage, [Validators.required, numberCheckValidator()]),
      'durationH': new FormControl(this.data.route.durationH, [Validators.required, numberCheckValidator()]),
    });
  }

  updatedRoute !: Route;

    onSubmitUpdate() {
      this.updatedRoute = {
        routeID: this.data.route.routeID,
        departure: this.sampleForm.value['departure'],
        destination: this.sampleForm.value['destination'],
        mileage: this.sampleForm.value['mileage'],
        durationH: this.sampleForm.value['durationH'],
      };

      this.routeService.updateRoute(this.updatedRoute);
      this.onNoClickWithoutConfirmation();

  }

  createdRoute !: Route;
  onSubmitCreate() {
    this.createdRoute = {
      routeID: NaN,
      departure: this.sampleForm.value['departure'],
      destination: this.sampleForm.value['destination'],
      mileage: this.sampleForm.value['mileage'],
      durationH: this.sampleForm.value['durationH'],
    };
    this.routeService.createRoute(this.createdRoute);
    this.onNoClickWithoutConfirmation();

  }


  areSameValues(): boolean {
      if (
        this.sampleForm.value['departure'] == this.data.route.departure &&
        this.sampleForm.value['destination'] == this.data.route.destination &&
        this.sampleForm.value['mileage'] == this.data.route.mileage &&
        this.sampleForm.value['durationH'] == this.data.route.durationH
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

  resetValues(): void {
    console.log(this.data.route.departure);
    this.sampleForm.patchValue({
      'departure': this.data.route.departure,
      'destination': this.data.route.destination,
      'mileage': this.data.route.mileage,
      'durationH': this.data.route.durationH,
    });
  }

  constructor(private routeService: RouteService,
              public dialogRef: MatDialogRef<FormComponent>,
              @Inject(MAT_DIALOG_DATA) public data: {
                route: Route,
                type: string
              }) {
    // this.formData = [NaN, '', '', NaN, NaN];
  }







}
