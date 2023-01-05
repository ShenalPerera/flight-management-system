import {Component, Inject} from '@angular/core';


@Component({
  selector: 'app-routes-screen',
  templateUrl: './routes-screen.component.html',
  styleUrls: ['./routes-screen.component.scss']
})
export class RoutesScreenComponent {
  formDataInRouteScreen !: [number, string, string, number, number, number];
  getFormData(data: [number, string, string, number, number, number]) {
    console.log('screen received: ' + data);
    this.formDataInRouteScreen = data;
  }
}
