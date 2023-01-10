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

      this.routeService.updateRoute(this.updatedRoute);
      this.onNoClickWithoutConfirmation();

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

  resetValues(): void {
    console.log(this.data.departure);
    this.sampleForm.patchValue({
      'departure': this.data.departure,
      'destination': this.data.destination,
      'mileage': this.data.mileage,
      'durationH': this.data.durationH,
    });
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





}
