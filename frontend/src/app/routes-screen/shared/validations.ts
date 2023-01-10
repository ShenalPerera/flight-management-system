import {AbstractControl, ValidationErrors, ValidatorFn} from "@angular/forms";

export function numberCheckValidator(): ValidatorFn {

  return (control: AbstractControl): ValidationErrors | null => {

    if (isNaN(control.value)) {
      return {forbiddenNumber: {value: control.value}};
    }else {
      return null;
    }
  }
}

