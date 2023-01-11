import {Component, Inject, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

import { Route } from '../models/route';
import {RouteService} from "../services/route.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {locationsValidator, numberCheckValidator} from "../shared/validations";



@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit{

  departure!: string;
  destination!: string;
  mileage!: number;
  durationH!: number;

  sampleForm !: FormGroup;

  updatedRoute !: Route;
  createdRoute !: Route;

  constructor(private routeService: RouteService,
              public dialogRef: MatDialogRef<FormComponent>,
              @Inject(MAT_DIALOG_DATA) public data: {
                route: Route,
                type: string
              }) {
  }
  ngOnInit() {
    this.sampleForm = new FormGroup({
      'departure': new FormControl(this.data.route.departure, [Validators.required]),
      'destination': new FormControl(this.data.route.destination, [Validators.required]),
      'mileage': new FormControl(this.data.route.mileage, [Validators.required, numberCheckValidator()]),
      'durationH': new FormControl(this.data.route.durationH, [Validators.required, numberCheckValidator()]),
    }, {validators: locationsValidator});
  }

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
    this.sampleForm.patchValue({
      'departure': this.data.route.departure,
      'destination': this.data.route.destination,
      'mileage': this.data.route.mileage,
      'durationH': this.data.route.durationH,
    });
  }

}
