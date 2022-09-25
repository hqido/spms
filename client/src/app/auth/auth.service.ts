import {Injectable} from '@angular/core';
import {Request} from "@app/request";
import {User} from "@app/context/user";
import {Context} from "@app/context/context";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private request: Request) {
  }

  public async getCurrentUser() {
    return new Promise<User>(
      resolve => {
        if (Context.user) {
          resolve(Context.user);
          return;
        }

        this.request.request<User>({
            api: '/api/auth/getCurrentUser',
            method: "GET",
            successCallback: data => {
              Context.user = data;
              resolve(data);
            }
          }
        );
      });
  }
}
