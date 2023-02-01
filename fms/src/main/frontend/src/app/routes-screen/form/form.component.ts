import {Component, Inject, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

import { Route } from '../models/route';
import {RouteService} from "../services/route.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {locationsValidator, numberCheckValidator} from "../shared/validations";
import {HttpStatusCodesFMS} from "../../http-status-codes-fms/httpStatusCodes.enum";




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
                airports: string[],
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

    //
    // if (this.data.type === 'edit') {
    //   this.sampleForm = new FormGroup({
    //     'departure': new FormControl({value: this.data.route.departure.toUpperCase(), disabled: true}),
    //     'destination': new FormControl({value: this.data.route.destination.toUpperCase(), disabled: true}),
    //     'mileage': new FormControl(mileageString, [Validators.required, numberCheckValidator()]),
    //     'durationH': new FormControl(durationString, [Validators.required, numberCheckValidator()]),
    //   }, {validators: locationsValidator});
    // }

    // if (this.data.type === 'create') {
    //   this.sampleForm = new FormGroup({
    //     'departure': new FormControl(this.data.route.departure.toUpperCase(), [Validators.required]),
    //     'destination': new FormControl(this.data.route.destination.toUpperCase(), [Validators.required]),
    //     'mileage': new FormControl(mileageString, [Validators.required, numberCheckValidator()]),
    //     'durationH': new FormControl(durationString, [Validators.required, numberCheckValidator()]),
    //   }, {validators: locationsValidator});
    // }


  }

  onSubmitUpdateInBackend() {
      this.updatedRoute = {
        routeID: this.data.route.routeID,
        departure: this.sampleForm.value['departure'].toLowerCase(),
        destination: this.sampleForm.value['destination'].toLowerCase(),
        mileage: +this.sampleForm.value['mileage'],
        durationH: +this.sampleForm.value['durationH'],
        createdDateTime : '',
        modifiedDateTime : '',
        version : this.data.route.version
      };

      this.routeService.updateRouteInBackend(this.updatedRoute)
        .subscribe({
          next: (resp)=>{
            if (resp.status == HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND) {
              alert('Sorry! That route is already there.');
            } else if (resp.status == HttpStatusCodesFMS.VERSION_MISMATCHED) {
              alert('Sorry, there are new updates. Fetching new updates');
              this.afterApplyClosing();
              // if (confirm('Sorry there are new updates. Do you want to fetch them ?')) {
              //   this.afterApplyClosing();
              // }
            } else if (resp.status == HttpStatusCodesFMS.ENTRY_NOT_FOUND) {
              alert('Sorry, that route has been already deleted.')
              this.afterApplyClosing();
            }
            else{
              alert("The route has been successfully updated.");
              this.afterApplyClosing();
            }

          },
          // error: (e)=>{
          //   alert('Sorry! That route is already there.')
          // }
        })


  }



  onSubmitCreateInBackend() {
    this.createdRoute = {
      routeID: NaN,
      departure: this.sampleForm.value['departure'].toLowerCase(),
      destination: this.sampleForm.value['destination'].toLowerCase(),
      mileage: +this.sampleForm.value['mileage'],
      durationH: +this.sampleForm.value['durationH'],
      createdDateTime : '',
      modifiedDateTime : '',
      version : NaN
    }
    this.routeService.createRouteInBackend(this.createdRoute)
      .subscribe({
        next: (resp)=>{
          if (resp.status == HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND) {
            alert('Sorry! That route is already there.');
          }else {
            alert("The route has been successfully created.");
            this.afterApplyClosing();
          }

        },
        // error: (e)=>{
        //   alert('Sorry! That route is already there.');
        // }
      })


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

  afterApplyClosing(): void {
    this.dialogRef.close({reloadData: true});
  }

  afterDiscardClosing(): void {
    if (this.areSameValues()) {
      this.dialogRef.close({reloadData: false});
    }else {
      if(confirm('Note: Unsaved changes will be discarded.')) {
        this.dialogRef.close({reloadData: false});
      }
    }

  }


  resetValues(): void {
    this.sampleForm.patchValue({
      'departure': this.data.route.departure.toUpperCase(),
      'destination': this.data.route.destination.toUpperCase(),
      'mileage': this.data.route.mileage.toString(),
      'durationH': this.data.route.durationH.toString(),
    });
  }

  clearValues(): void {
    this.sampleForm.patchValue({
      'departure': '',
      'destination': '',
      'mileage': '',
      'durationH': '',
    });
  }

}
