export default class ErrorResponse {
    private _status: number;
    private _title: string;
    private _path: string;
    private _messages: string[];

  
    public constructor(status: number, title: string, path: string, message: string[]) {
      this._status = status;
      this._title = title;
      this._path = path;
      this._messages = message;
    }


    get status(): number {
        return this._status;
    }

    get title(): string {
        return this._title;
    }

    get path(): string {
        return this._path;
    }

    get message(): string[] {
        return this._messages;
    }
}