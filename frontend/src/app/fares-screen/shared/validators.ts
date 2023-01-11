import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export const locationValidator: ValidatorFn =
  (control: AbstractControl): ValidationErrors | null => {
    const departure = control.get('departure');
    const arrival = control.get('arrival');
    if (departure?.value == arrival?.value) {
      return { sameLocation : true};
    }
    return null;
  };
