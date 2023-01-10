import {AbstractControl, ValidationErrors, ValidatorFn} from "@angular/forms";

// export function stringCheckValidator(): ValidatorFn {
//
//   return (control: AbstractControl): ValidationErrors | null => {
//     // console.log("input field has the value: "+control.value);
//     if (/\d/.test(control.value)) {
//       return {forbiddenName: {value: control.value}};
//     }else {
//       return null;
//     }
//   }
// }


export function numberCheckValidator(): ValidatorFn {

  return (control: AbstractControl): ValidationErrors | null => {
    // console.log("input field has the value: "+typeof control.value);
    if (isNaN(control.value)) {
      return {forbiddenNumber: {value: control.value}};
    }else {
      return null;
    }
  }
}

// export function stringCheckValidator(nameRe: RegExp): ValidatorFn {
//   return (control: AbstractControl): ValidationErrors | null => {
//     console.log("input field has the value: "+control.value);
//     if (nameRe.test(control.value)) {
//       return {forbiddenName: {value: control.value}};
//     }else {
//       return null;
//     }
//   };
// }
