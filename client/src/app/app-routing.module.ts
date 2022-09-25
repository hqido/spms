import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {IndexComponent} from "@app/index/index.component";
import {PageNotFoundComponent} from "@app/page-not-found/page-not-found.component";
import {LoginComponent} from "@app/login/login.component";
import {AuthGuard} from "@app/auth.guard";
import {HomeComponent} from "@app/home/home.component";

const routes: Routes = [
  {
    path: '', component: IndexComponent, title: "首页", canActivate: [AuthGuard], children: [
      {
        path: 'home', component: HomeComponent
      }
    ]
  },
  {path: 'login', component: LoginComponent, title: "登录"},
  {path: '**', component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
