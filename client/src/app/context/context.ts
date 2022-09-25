import {User} from "@app/context/user";

export class Context {

  static user?: User;

  static nickname(): string {
    return this.user == undefined ? '' : this.user.nickName;
  }
}
