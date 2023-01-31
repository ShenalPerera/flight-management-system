import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export const airportValidator: ValidatorFn =
  (control: AbstractControl): ValidationErrors | null => {
  const departure = control.get('departure')?.value;
  const arrival = control.get('arrival')?.value;
  if (!departure || !arrival)
    return null;
  if (departure == arrival) {
    return { sameLocation : true };
  }
  return null;
};

export const numberValidator: ValidatorFn =
  (control: AbstractControl): ValidationErrors | null => {
  if ((control.value == null) && control.dirty)
    return { notANumber : true };
  if ((control.value <= 0) && control.dirty)
    return { notAPositiveNumber : true };
  return null;
};
