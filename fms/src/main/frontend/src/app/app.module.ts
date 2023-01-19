import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatTableModule} from "@angular/material/table";
import { MatAutocompleteModule } from "@angular/material/autocomplete";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from "@angular/material/input";
import { MatDatepickerModule } from "@angular/material/datepicker";
import { MatNativeDateModule } from "@angular/material/core";
import { MatCardModule } from "@angular/material/card";
import { MatButtonModule } from "@angular/material/button";
import { MatIconModule } from "@angular/material/icon";
import { MatSelectModule } from "@angular/material/select";
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from "@angular/material/dialog";
import { MatExpansionModule } from "@angular/material/expansion";

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RoutesScreenComponent } from './routes-screen/routes-screen.component';
import { FaresScreenComponent } from './fares-screen/fares-screen.component';
import { FlightsScreenComponent } from './flights-screen/flights-screen.component';
import { HeaderComponent } from "./header/header.component";
import { FlightItemComponent } from './flights-screen/flight-item/flight-item.component';
import { DataFilterPipe } from './data-filter.pipe';
import { FormComponent } from './routes-screen/form/form.component';
import { FareItemComponent } from './fares-screen/fare-item/fare-item.component';
import { FareFormComponent } from './fares-screen/fare-form/fare-form.component';
import { RouteService } from "./routes-screen/services/route.service";
import { EntryComponent } from "./routes-screen/entry/entry.component";
import {HttpClientModule} from "@angular/common/http";


@NgModule({
  declarations: [
    AppComponent,
    RoutesScreenComponent,
    FaresScreenComponent,
    FlightsScreenComponent,
    HeaderComponent,
    HeaderComponent,
    FlightItemComponent,
    DataFilterPipe,
    FormComponent,
    EntryComponent,
    FareItemComponent,
    FareFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatTableModule,
    MatAutocompleteModule,
    FormsModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatSelectModule,
    BrowserAnimationsModule,
    MatDialogModule,
    MatExpansionModule
  ],
  providers: [
    RouteService,
    {
      provide: MatDialogRef,
      useValue: []
    },
    {
      provide: MAT_DIALOG_DATA,
      useValue: []
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
