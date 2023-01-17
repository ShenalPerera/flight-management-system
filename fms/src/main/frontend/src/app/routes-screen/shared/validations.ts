import {AbstractControl, FormGroup, ValidationErrors, ValidatorFn} from "@angular/forms";

export function numberCheckValidator(): ValidatorFn {

  return (control: AbstractControl): ValidationErrors | null => {

    if (control.value == '') {
      return null;
    }
    if (isNaN(+control.value)) {
      return {forbiddenNumber: {value: control.value}};
    }else if (+control.value<0) {
      return {negativeNumber: {value: control.value}};
    }else if(+control.value == 0) {
      return {zeroNumber: {value: control.value}};
    }else {
      return null;
    }
  }
}

export const locationsValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  const departure = control.get('departure');
  const destination = control.get('destination');
  if ((String(departure?.value).toLowerCase() == String(destination?.value).toLowerCase()) && (departure?.value != '') && (destination?.value != '')) {
    return {forbiddenLocations: true};
  }else {
    return null;
  }
};

