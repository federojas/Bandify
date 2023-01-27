import ErrorResponse from "./ErrorResponse";

export default class ApiResult<T> {
    private readonly data: T;
    private readonly error: ErrorResponse;
    private readonly failed: boolean;
  
    public constructor(data: T, failed: boolean, error: ErrorResponse) {
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
  
    public hasFailed(): boolean {
      return this.failed;
    }
  }