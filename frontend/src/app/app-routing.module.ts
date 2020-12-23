import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {BuddyrequestCreateComponent} from './buddyrequest-create/buddyrequest-create.component';
import {SuccessComponent} from './success/success.component';
import {BuddyrequestConfirmComponent} from './buddyrequest-confirm/buddyrequest-confirm.component';
import {BuddyrequestClearComponent} from './buddyrequest-clear/buddyrequest-clear.component';
import {ImpressumComponent} from './impressum/impressum.component';
import {PrivacypolicyComponent} from './privacypolicy/privacypolicy.component';

const routes: Routes = [
  {path: '', component: BuddyrequestCreateComponent},
  {path: 'confirm/:id', component: BuddyrequestConfirmComponent},
  {path: 'clear/:id', component: BuddyrequestClearComponent },
  {path: 'success', component: SuccessComponent},
  {path: 'impressum', component: ImpressumComponent},
  {path: 'privacypolicy', component: PrivacypolicyComponent},
  { path: '**', redirectTo: '/', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
