import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { Entry } from "../shared/entry.model";
import { locationValidator, numberValidator } from "../shared/validators";
import { FareService } from "../services/fare.service";
import { HttpStatusCodesFMS } from "../../http-status-codes-fms/httpStatusCodes.enum"

@Component({
  selector: 'app-fare-form',
  templateUrl: './fare-form.component.html',
  styleUrls: ['./fare-form.component.scss']
})
export class FareFormComponent implements OnInit {
  sampleForm!: FormGroup;
  constructor(
    private fareService: FareService,
    public dialogRef: MatDialogRef<FareFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {
      createEvent: boolean,
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
  }
  discardClicked() {
    if (
      (this.sampleForm.value['departure'].toLowerCase() == this.data.entry.departure) &&
      (this.sampleForm.value['arrival'].toLowerCase() == this.data.entry.arrival) &&
      (this.sampleForm.value['fare'] == this.data.entry.fare)) {
      this.dialogRef.close(false);
    } else if (confirm("Entered data will be lost! Do you want to proceed?")) {
      this.dialogRef.close(false);
    }
  }
  submitCLicked() {
    if (this.data.createEvent) {
      this.fareService.createEntry({
        id: this.data.entry.id,
        departure: this.sampleForm.value['departure'].toLowerCase(),
        arrival: this.sampleForm.value['arrival'].toLowerCase(),
        fare: this.sampleForm.value['fare'],
        createdTimestamp: "",
        modifiedTimestamp: "",
        version: 0
      }).subscribe({
        next: (response) => {
          if (response.status == HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND)
            alert("The entry is already in the system!")
          else
            this.dialogRef.close(true)
        }
      });
    } else {
      this.fareService.editEntry({
        id: this.data.entry.id,
        departure: this.sampleForm.value['departure'].toLowerCase(),
        arrival: this.sampleForm.value['arrival'].toLowerCase(),
        fare: this.sampleForm.value['fare'],
        createdTimestamp: this.data.entry.createdTimestamp,
        modifiedTimestamp: this.data.entry.modifiedTimestamp,
        version: this.data.entry.version
      }).subscribe({
        next: (response) => {
          if (response.status == HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND)
            alert("The entry is already in the system!")
          else if (response.status == 0)
            alert("Someone else has changed the entry, reload the page and try again!");
          else
            this.dialogRef.close(true)
        }
      });
    }
  }
  resetClicked() {
    this.sampleForm.patchValue({
      'departure': this.data.entry.departure,
      'arrival': this.data.entry.arrival,
      'fare': this.data.entry.fare
    });
    this.sampleForm.markAsPristine();
  }
}
