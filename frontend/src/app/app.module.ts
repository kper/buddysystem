import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BuddyrequestCreateComponent } from './buddyrequest-create/buddyrequest-create.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import { SuccessComponent } from './success/success.component';
import { BuddyrequestConfirmComponent } from './buddyrequest-confirm/buddyrequest-confirm.component';
import {BuddyrequestClearComponent} from './buddyrequest-clear/buddyrequest-clear.component';
import { ImpressumComponent } from './impressum/impressum.component';
import { PrivacypolicyComponent } from './privacypolicy/privacypolicy.component';

@NgModule({
  declarations: [
    AppComponent,
    BuddyrequestCreateComponent,
    SuccessComponent,
    BuddyrequestConfirmComponent,
    BuddyrequestClearComponent,
    ImpressumComponent,
    PrivacypolicyComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        ReactiveFormsModule,
        NgbModule,
        HttpClientModule,
        FormsModule,
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
