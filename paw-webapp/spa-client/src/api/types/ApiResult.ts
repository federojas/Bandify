import ErrorResponse from "./ErrorResponse";

export default class ApiResult<T> {
    private data: T;
    private error: ErrorResponse;
    private failed: boolean;
  
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