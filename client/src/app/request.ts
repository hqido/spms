import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {environment} from "@env/environment";
import {Observable} from "rxjs";
import {Result} from "@app/common/base";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class Request {

  private sever = environment.server;

  constructor(private http: HttpClient, private router: Router, private snackBar: MatSnackBar) {
  }

  request<R>(option: RequestOption<R>): void {
    let r: Observable<Result<R>>;
    let url: string = this.sever + option.api
    let options = {
      headers: option.headers,
      params: option.params
    }
    if (option.method == 'GET') {
      r = this.http.get<Result<R>>(url, options);
    } else if (option.method == 'POST') {
      r = this.http.post<Result<R>>(url, option.body, options);
    } else {
      alert("not support http method");
      throw new Error("not support http method");
    }

    r.subscribe({
        next: v => this.handleSuccessRequest(v, option, this),
        error: e => this.handleError(e, this)
      }
    );
  }

  private handleError(error: HttpErrorResponse, that: Request) {
    that.alert(error.message);
  }

  private handleSuccessRequest<R>(result: Result<R>, option: RequestOption<R>, that: Request): void {
    // read result code (biz)
    const code: string = result.code;

    // global code handle
    if (code == 'SUCCESS') {
      if (option.successCallback && result.data) {
        option.successCallback(result.data);
      }
      // jump login page
    } else if (code == 'NOT_LOGIN' || code == 'INVALID_LOGIN') {
      that.alert("user not login");
      this.router.navigate(['/login']);
    } else {
      that.alert(result.message);
    }
  }

  private alert(message: string) {
    this.snackBar.open(message, 'Close', {
      duration: 5 * 1000
    })
  }

}

interface RequestOption<R> {

  /**
   * api address
   * /api/test
   */
  api: string;

  /**
   * http method
   */
  method: 'GET' | 'POST';

  /**
   *  header
   */
  headers?: {},

  /**
   * params
   */
  params?: {};

  /**
   * request
   */
  body?: any;

  /**
   * response.code = 'SUCCESS'
   * @param data
   */
  successCallback?: (data: R) => void;
}
