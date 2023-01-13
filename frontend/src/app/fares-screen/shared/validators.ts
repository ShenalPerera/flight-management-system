import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export const locationValidator: ValidatorFn =
  (control: AbstractControl): ValidationErrors | null => {
  const departure = control.get('departure');
  const arrival = control.get('arrival');
  if (
    (departure?.value.toLowerCase() == arrival?.value.toLowerCase()) &&
    (departure?.value != '') && (arrival?.value != '')
  ) {
    return { sameLocation : true };
  }
  return null;
};

export const numberValidator: ValidatorFn =
  (control: AbstractControl): ValidationErrors | null => {
  if (control.value == null)
    return { notANumber : true };
  if (control.value < 0)
    return { notAPositiveNumber : true };
  return null;
};
