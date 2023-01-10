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
      locations: string[]
    }
  ) {}
  closeDialog() {
    this.dialogRef.close(this.updatedFare);
  }
  sampleForm !: FormGroup;
  ngOnInit() {
    this.sampleForm = new FormGroup({
      'departure': new FormControl(this.data.entry.departure, [Validators.required]),
      'arrival': new FormControl(this.data.entry.arrival, [Validators.required]),
      'fare': new FormControl(this.data.entry.fare, [Validators.required])
    });
  }
  updatedFare !: Entry;
  onSubmitUpdate() {
    this.updatedFare = {
      id: this.data.entry.id,
      departure: this.sampleForm.value['departure'],
      arrival: this.sampleForm.value['arrival'],
      fare: this.sampleForm.value['fare'],
    }
    this.closeDialog();
  }
}
