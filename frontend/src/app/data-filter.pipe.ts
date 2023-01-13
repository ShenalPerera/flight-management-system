import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dataFilter',
  pure:false
})
export class DataFilterPipe implements PipeTransform {

  transform(value: any, data_field: any, property_name: any): any {
    if(value.length === 0 || data_field === '' || data_field === null){
      return value;
    }

    const resultArray = [];

    for (const item of value){
      if (!(property_name === "flight_number") && item[property_name].toLowerCase().includes(data_field.toLowerCase())) {
        resultArray.push(item);
      }
      else if (item[property_name].toLowerCase() == data_field.toLowerCase()){
        resultArray.push(item);
      }
    }

    return resultArray;
  }

}
