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
import { MatButtonModule} from "@angular/material/button";
import { MatIconModule } from "@angular/material/icon";
// import { DetailsComponent } from './routes-screen/details/details.component';
import { FormComponent } from './routes-screen/form/form.component';
import { MatSelectModule } from "@angular/material/select";
import { MatInputModule } from "@angular/material/input";

import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { EntryComponent } from './routes-screen/entry/entry.component';

import {RouteService} from "./routes-screen/services/route.service";

@NgModule({
  declarations: [
    AppComponent,
    RoutesScreenComponent,
    FaresScreenComponent,
    // DetailsComponent,
    FormComponent,
    EntryComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    // NoopAnimationsModule,
    MatTableModule,
    MatAutocompleteModule,
    FormsModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatIconModule,
    MatSelectModule,
    MatInputModule,
    BrowserAnimationsModule,
  ],
  providers: [
    RouteService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
