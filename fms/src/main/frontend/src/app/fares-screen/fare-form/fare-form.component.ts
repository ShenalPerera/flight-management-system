import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { Entry } from "../shared/entry.model";
import { locationValidator, numberValidator } from "../shared/validators";
import { FareService } from "../services/fare.service";

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
      this.dialogRef.close();
    } else if (confirm("Entered data will be lost! Do you want to proceed?")) {
      this.dialogRef.close();
    }
  }
  submitCLicked() {
    if (this.data.createEvent) {
      this.fareService.createEntry({
        id: this.data.entry.id,
        departure: this.sampleForm.value['departure'].toLowerCase(),
        arrival: this.sampleForm.value['arrival'].toLowerCase(),
        fare: this.sampleForm.value['fare']
      }).subscribe((data) => {
        if (data === null) {
          alert("The entry is already in the system!");
        } else {
          this.dialogRef.close();
        }
      })
    } else {
      this.fareService.editEntry({
        id: this.data.entry.id,
        departure: this.sampleForm.value['departure'].toLowerCase(),
        arrival: this.sampleForm.value['arrival'].toLowerCase(),
        fare: this.sampleForm.value['fare']
      }).subscribe((data) => {
        if (data === null) {
          alert("The entry is already in the system!");
        } else {
          this.dialogRef.close();
        }
      })
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

  // submitCLickedOld() {
  //   let duplicateId = this.fareService.isDuplicate(
  //     this.sampleForm.value['departure'].toLowerCase(),
  //     this.sampleForm.value['arrival'].toLowerCase()
  //   )
  //   if (this.data.createEvent) {
  //     if (duplicateId != 0) {
  //       alert("The entry is already in the database!");
  //     } else if (confirm("Do you want to create the fare of the route, from "+
  //       this.sampleForm.value['departure'].toUpperCase()+" to "+
  //       this.sampleForm.value['arrival'].toUpperCase()+" as "+
  //       this.sampleForm.value['fare']+"?")) {
  //       this.dialogRef.close({
  //         id: this.data.entry.id,
  //         departure: this.sampleForm.value['departure'].toLowerCase(),
  //         arrival: this.sampleForm.value['arrival'].toLowerCase(),
  //         fare: this.sampleForm.value['fare']
  //       });
  //     }
  //   } else {
  //     if ((duplicateId != this.data.entry.id) && (duplicateId != 0)) {
  //       alert("The entry is already in the database!");
  //     } else {
  //       this.dialogRef.close({
  //         id: this.data.entry.id,
  //         departure: this.sampleForm.value['departure'].toLowerCase(),
  //         arrival: this.sampleForm.value['arrival'].toLowerCase(),
  //         fare: this.sampleForm.value['fare'],
  //       });
  //     }
  //   }
  // }
}
