import ApiResult from "../api/types/ApiResult";
import ErrorResponse from "../api/types/ErrorResponse";

export class ErrorService {
    public static async returnApiError(error: any): Promise<ApiResult<any>> {
        return new ApiResult(null as any,
            true, new ErrorResponse(error.response.data.status, error.response.data.title,
                error.response.data.path, error.response.data.message));
    }
}




