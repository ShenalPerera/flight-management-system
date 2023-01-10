import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { Entry } from "../fares-screen.component";
import { FormControl, FormGroup, Validators } from "@angular/forms";

@Component({
  selector: 'app-fare-form',
  templateUrl: './fare-form.component.html',
  styleUrls: ['./fare-form.component.scss']
})
export class FareFormComponent implements OnInit {
  constructor(
    public dialogRef: MatDialogRef<FareFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {
      type: string,
      entry: Entry,
      departingLocation: string[],
      arrivingLocation: string[]
    }
  ) {}
  closeDialog() {
    if (confirm("Entered data will be lost! Do you want to proceed?"))
      this.dialogRef.close(this.updatedFare);
  }
  sampleForm !: FormGroup;
  originalEntry !: Entry;
  ngOnInit() {
    this.sampleForm = new FormGroup({
      'departure': new FormControl(this.data.entry.departure, [Validators.required]),
      'arrival': new FormControl(this.data.entry.arrival, [Validators.required]),
      'fare': new FormControl(this.data.entry.fare, [Validators.required])
    });
    this.originalEntry = {
      id: this.data.entry.id,
      departure: this.data.entry.departure,
      arrival: this.data.entry.arrival,
      fare: this.data.entry.fare,
    }
  }
  updatedFare !: Entry;
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
      'fare': new FormControl(this.originalEntry.fare, [Validators.required])
    });
  }
}
