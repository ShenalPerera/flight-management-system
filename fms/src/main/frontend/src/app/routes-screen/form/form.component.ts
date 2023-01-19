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
    let mileageString = this.data.route.mileage.toString();
    let durationString = this.data.route.durationH.toString();
    if (mileageString == '0') {
      mileageString = '';
    }
    if (durationString == '0') {
      durationString = '';
    }

    this.sampleForm = new FormGroup({
      'departure': new FormControl(this.data.route.departure.toUpperCase(), [Validators.required]),
      'destination': new FormControl(this.data.route.destination.toUpperCase(), [Validators.required]),
      'mileage': new FormControl(mileageString, [Validators.required, numberCheckValidator()]),
      'durationH': new FormControl(durationString, [Validators.required, numberCheckValidator()]),
    }, {validators: locationsValidator});
  }

  onSubmitUpdate() {

    let hasDuplicates = this.routeService.handleDuplicatesWhenUpdating
    (
      this.sampleForm.value['departure'].toLowerCase(),
      this.sampleForm.value['destination'].toLowerCase(),
      this.data.route.routeID
    );

    if(!hasDuplicates) {
      this.updatedRoute = {
        routeID: this.data.route.routeID,
        departure: this.sampleForm.value['departure'],
        destination: this.sampleForm.value['destination'],
        mileage: +this.sampleForm.value['mileage'],
        durationH: +this.sampleForm.value['durationH'],
      };

      this.routeService.updateRoute(this.updatedRoute);
      this.onNoClickWithoutConfirmation();
    }else{
      confirm('Sorry! That route is already there.')
    }

  }

  onSubmitCreate() {

    let hasDuplicates = this.routeService.handleDuplicatesWhenCreating(
      this.sampleForm.value['departure'].toLowerCase(),
      this.sampleForm.value['destination'].toLowerCase()
    );

    if(!hasDuplicates) {
        this.createdRoute = {
          routeID: NaN,
          departure: this.sampleForm.value['departure'],
          destination: this.sampleForm.value['destination'],
          mileage: +this.sampleForm.value['mileage'],
          durationH: +this.sampleForm.value['durationH'],
        };
        this.routeService.createRoute(this.createdRoute);
        this.onNoClickWithoutConfirmation();
      }else{
      confirm('Sorry! That route is already there.')
    }
  }

  areSameValues(): boolean {
      if (
        this.sampleForm.value['departure'].toLowerCase() == this.data.route.departure &&
        this.sampleForm.value['destination'].toLowerCase() == this.data.route.destination &&
        +this.sampleForm.value['mileage'] == this.data.route.mileage &&
        +this.sampleForm.value['durationH']== this.data.route.durationH
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
        if(confirm('Note: Unsaved changes will be discarded.')) {
          this.dialogRef.close();
        }
      }

  }

  onNoClickWithoutConfirmation(): void {
    this.dialogRef.close();
  }

  resetValues(): void {
    this.sampleForm.patchValue({
      'departure': this.data.route.departure.toUpperCase(),
      'destination': this.data.route.destination.toUpperCase(),
      'mileage': this.data.route.mileage.toString(),
      'durationH': this.data.route.durationH.toString(),
    });
  }

}
