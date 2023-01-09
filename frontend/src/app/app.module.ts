import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RoutesScreenComponent } from './routes-screen/routes-screen.component';
import { FaresScreenComponent } from './fares-screen/fares-screen.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { MatTableModule} from "@angular/material/table";
import { MatAutocompleteModule } from "@angular/material/autocomplete";
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FlightsComponent } from './flights/flights.component';
import {HeaderComponent} from "./header/header.component";
import { FlightItemComponent } from './flights/flight-item/flight-item.component';
import { DataFilterPipe } from './data-filter.pipe';
import {MatInputModule} from "@angular/material/input";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatNativeDateModule} from "@angular/material/core";
import {MatCardModule} from "@angular/material/card";
import {MatButtonModule} from "@angular/material/button";




@NgModule({
    declarations: [
        AppComponent,
        RoutesScreenComponent,
        FaresScreenComponent,
        FlightsComponent,
        HeaderComponent,
        HeaderComponent,
        FlightItemComponent,
        DataFilterPipe,

    ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NoopAnimationsModule,
    MatTableModule,
    MatAutocompleteModule,
    FormsModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatCardModule,
    MatButtonModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
