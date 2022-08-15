import {User} from "@app/context/user";

export class Context {

  static user?: User;

  static getName(): string {
    return this.user == undefined ? '' : this.user.name;
  }
}
