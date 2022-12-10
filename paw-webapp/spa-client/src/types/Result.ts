import ErrorResponse from "./ErrorResponse";

export default class Result<T> {
  private readonly data: T;
  private readonly error: ErrorResponse;
  private readonly failed: boolean;

  private constructor(data: T, failed: boolean, error: ErrorResponse) {
    this.data = data;
    this.failed = failed;
    this.error = error;
  }

  public getData(): T {
    return this.data;
  }

  public getError(): ErrorResponse {
    return this.error;
  }

  public static ok<T>(data: T) {
    return new Result<T>(data, false, null as any);
  }

  public static failed(error: ErrorResponse) {
    return new Result(null as any, true, error);
  }

  public hasFailed(): boolean {
    return this.failed;
  }
}
