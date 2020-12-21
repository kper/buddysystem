import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {BuddyrequestCreateComponent} from './buddyrequest-create/buddyrequest-create.component';
import {SuccessComponent} from './success/success.component';
import {BuddyrequestConfirmComponent} from './buddyrequest-confirm/buddyrequest-confirm.component';

const routes: Routes = [
  {path: '', component: BuddyrequestCreateComponent},
  {path: 'confirm/:id', component: BuddyrequestConfirmComponent},
  {path: 'success', component: SuccessComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
