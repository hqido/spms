import {Injectable} from '@angular/core';
import {Context} from "@app/context/context";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() {
  }

  isLogin(): boolean {
    return Context.user === null;
  }

}
