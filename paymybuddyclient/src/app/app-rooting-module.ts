import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {AuthenticationComponent} from "./authentication/authentication.component";
import {SubscriptionComponent} from "./subscription/subscription.component";
import {NavigationComponent} from "./navigation/navigation.component";
import {TransferComponent} from "./transfer/transfer.component";
import {ContactComponent} from "./contact/contact.component";

const routes: Routes = [
  { path: '', component: AuthenticationComponent },
  { path: 'subscription', component: SubscriptionComponent},
  { path:'home', component: NavigationComponent },
  { path:'transfer', component: TransferComponent},
  { path:'profile', component: NavigationComponent },
  { path:'contact', component: ContactComponent },
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
