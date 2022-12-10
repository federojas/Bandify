export default class ErrorResponse {
    private readonly code: number;
    private readonly description: string;
  
    public constructor(code: number, description: string) {
      this.code = code;
      this.description = description;
    }
  
    public getCode(): number {
      return this.code;
    }
  
    public getDescription(): string {
      return this.description;
    }
  }
  