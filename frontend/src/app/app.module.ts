import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RoutesScreenComponent } from './routes-screen/routes-screen.component';
import { FaresScreenComponent } from './fares-screen/fares-screen.component';
import { NoopAnimationsModule, BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MatTableModule} from "@angular/material/table";
import { MatAutocompleteModule } from "@angular/material/autocomplete";
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { SearchBarComponent } from './fares-screen/search-bar/search-bar.component';
import {MatInputModule} from "@angular/material/input";
import {MatSelectModule} from "@angular/material/select";



@NgModule({
  declarations: [
    AppComponent,
    RoutesScreenComponent,
    FaresScreenComponent,
    SearchBarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatTableModule,
    MatAutocompleteModule,
    MatSelectModule,
    ReactiveFormsModule,
    MatInputModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
