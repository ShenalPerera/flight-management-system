import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { Fare } from "../shared/entry.model";
import { airportValidator, numberValidator } from "../shared/validators";
import { FareService } from "../services/fare.service";
import { HttpStatusCodesFMS } from "../../http-status-codes-fms/httpStatusCodes.enum"

@Component({
  selector: 'app-fare-form',
  templateUrl: './fare-form.component.html',
  styleUrls: ['./fare-form.component.scss']
})
export class FareFormComponent implements OnInit {
  formGroup!: FormGroup;
  constructor(
    private fareService: FareService,
    public dialogRef: MatDialogRef<FareFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {
      createEvent: boolean,
      entry: Fare,
      airports: string[]
    }
  ) {}

  ngOnInit() {
    this.formGroup = new FormGroup({
      'departure': new FormControl({ value: this.data.entry.departure, disabled: !this.data.createEvent }, [Validators.required]),
      'arrival': new FormControl({ value: this.data.entry.arrival, disabled: !this.data.createEvent }, [Validators.required]),
      'fare': new FormControl(this.data.entry.fare, [Validators.required, numberValidator])
    }, { validators: airportValidator });
  }
  discardClicked() {
    if (this.formGroup.pristine.valueOf()) {
      this.dialogRef.close(false);
    } else if (confirm("Entered data will be lost! Do you want to proceed?")) {
      this.dialogRef.close(false);
    }
  }
  submitCLicked() {
    if (this.data.createEvent) {
      this.fareService.createEntry({
        fareId: this.data.entry.fareId,
        departure: this.formGroup.value['departure'].toLowerCase(),
        arrival: this.formGroup.value['arrival'].toLowerCase(),
        fare: this.formGroup.value['fare'],
        version: 0
      }).subscribe({
        next: (response) => {
          if (response.status == HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND)
            alert("The fare is already in the system!")
          else if (response.status == HttpStatusCodesFMS.ROUTE_DOESNT_EXIST)
            alert("Route does not exist! Cannot create the Fare.")
          else
            this.dialogRef.close(true)
        },
        error: () => alert("An unexpected error occurred!")
      });
    } else {
      this.fareService.editEntry({
        fareId: this.data.entry.fareId,
        departure: this.data.entry.departure,
        arrival: this.data.entry.arrival,
        fare: this.formGroup.value['fare'],
        version: this.data.entry.version
      }).subscribe({
        next: (response) => {
          if (response.status == HttpStatusCodesFMS.DUPLICATE_ENTRY_FOUND) {
            alert("The fare is already in the system!")
          } else if (response.status == HttpStatusCodesFMS.VERSION_MISMATCHED) {
            alert("This Fare is already updated by someone!");
            this.dialogRef.close(false)
          } else {
            this.dialogRef.close(true)
          }
        },
        error: () => alert("An unexpected error occurred!")
      });
    }
  }
  resetClicked() {
    if (this.data.createEvent)
      this.formGroup.reset();
    else {
      this.formGroup.patchValue({
        'departure': this.data.entry.departure,
        'arrival': this.data.entry.arrival,
        'fare': this.data.entry.fare
      });
      this.formGroup.markAsPristine();
    }
  }
}
