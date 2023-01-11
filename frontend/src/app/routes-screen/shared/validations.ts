import {AbstractControl, FormGroup, ValidationErrors, ValidatorFn} from "@angular/forms";

export function numberCheckValidator(): ValidatorFn {

  return (control: AbstractControl): ValidationErrors | null => {

    if (isNaN(control.value)) {
      return {forbiddenNumber: {value: control.value}};
    }else if (control.value<0) {
      return {negativeNumber: {value: control.value}};
    }else {
      return null;
    }
  }
}

export const locationsValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  const departure = control.get('departure');
  const destination = control.get('destination');
  if ((departure?.value == destination?.value) && (departure?.value != '') && (destination?.value != '')) {
    return {forbiddenLocations: true};
  }else {
    return null;
  }
};

