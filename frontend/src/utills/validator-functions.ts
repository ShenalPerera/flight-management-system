import {AbstractControl, ValidatorFn} from "@angular/forms";

export const arrivalDatesValidator: ValidatorFn = (control: AbstractControl) => {
  const departureDate= control.get('oDepartureDateNTime')?.value;
  const arrivalDate = control.get('oArrivalDateNTime')?.value;

  if (!arrivalDate || !departureDate){
    return null;
  }

  const arrivalDateObject:Date = new Date(arrivalDate);
  const departureDateObject:Date = new Date(departureDate);

  if (arrivalDateObject.getTime() <= departureDateObject.getTime()){
    return {isDateValid:true};
  }
  return null;

}
