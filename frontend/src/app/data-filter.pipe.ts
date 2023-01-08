import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dataFilter',
  pure:false
})
export class DataFilterPipe implements PipeTransform {

  transform(value: any, data_field: any, property_name: any): any {
    if(value.length === 0 || data_field === ' '){
      return value;
    }

    const resultArray = [];

    for (const item of value){
      if (item[property_name] == data_field){
        resultArray.push(item);
      }
    }

    return resultArray;
  }

}
