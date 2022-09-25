export interface Result<R> {

  code: string;

  message: string;

  data?: R;

}
