import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {AuthenticationComponent} from "./authentication/authentication.component";
import {SubscriptionComponent} from "./subscription/subscription.component";

const routes: Routes = [
  { path: '', component: AuthenticationComponent },
  { path: 'transfer', component: AuthenticationComponent },
  { path: 'subscription', component: SubscriptionComponent},
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule{}
