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



@NgModule({
  declarations: [
    AppComponent,
    RoutesScreenComponent,
    FaresScreenComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NoopAnimationsModule,
    MatTableModule,
    MatAutocompleteModule,
    FormsModule,
    MatFormFieldModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
