import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { Entry } from "../shared/entry.model";
import { locationValidator, numberValidator } from "../shared/validators";

@Component({
  selector: 'app-fare-form',
  templateUrl: './fare-form.component.html',
  styleUrls: ['./fare-form.component.scss']
})
export class FareFormComponent implements OnInit {
  sampleForm!: FormGroup;
  originalEntry!: Entry;
  updatedFare!: Entry;
  constructor(
    public dialogRef: MatDialogRef<FareFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {
      type: string,
      entry: Entry,
      departingLocation: string[],
      arrivingLocation: string[]
    }
  ) {}
  ngOnInit() {
    this.sampleForm = new FormGroup({
      'departure': new FormControl(this.data.entry.departure, [Validators.required]),
      'arrival': new FormControl(this.data.entry.arrival, [Validators.required]),
      'fare': new FormControl(this.data.entry.fare, [Validators.required, numberValidator])
    }, { validators: locationValidator });
    this.originalEntry = {
      id: this.data.entry.id,
      departure: this.data.entry.departure,
      arrival: this.data.entry.arrival,
      fare: this.data.entry.fare,
    }
  }
  closeDialog() {
    if (confirm("Entered data will be lost! Do you want to proceed?"))
      this.dialogRef.close(this.updatedFare);
  }
  onSubmitUpdate() {
    this.updatedFare = {
      id: this.data.entry.id,
      departure: this.sampleForm.value['departure'],
      arrival: this.sampleForm.value['arrival'],
      fare: this.sampleForm.value['fare'],
    }
    this.dialogRef.close(this.updatedFare);
  }
  resetForm() {
    this.sampleForm = new FormGroup({
      'departure': new FormControl(this.originalEntry.departure, [Validators.required]),
      'arrival': new FormControl(this.originalEntry.arrival, [Validators.required]),
      'fare': new FormControl(this.originalEntry.fare, [Validators.required, numberValidator])
    }, { validators: locationValidator });
  }
}
