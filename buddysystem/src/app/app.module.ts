import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BuddyrequestCreateComponent } from './buddyrequest-create/buddyrequest-create.component';
import { ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import { SuccessComponent } from './success/success.component';
import { BuddyrequestConfirmComponent } from './buddyrequest-confirm/buddyrequest-confirm.component';

@NgModule({
  declarations: [
    AppComponent,
    BuddyrequestCreateComponent,
    SuccessComponent,
    BuddyrequestConfirmComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    NgbModule,
    HttpClientModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
