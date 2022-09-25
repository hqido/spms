import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree} from '@angular/router';
import {Context} from "@app/context/context";
import {AuthService} from "@app/auth/auth.service";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private authService: AuthService) {
  }

  async canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Promise<boolean | UrlTree> {

    if (!Context.user) {
      return await this.authService.getCurrentUser()
        .then(data => {
          console.info(Context.user);
          return data !== undefined;
        });
    }

    return true;
  }

}
