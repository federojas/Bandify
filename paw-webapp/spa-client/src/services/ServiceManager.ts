import { NavigateFunction } from "react-router-dom";
import ApiResult from "../api/types/ApiResult";

export async function serviceCall<T>(
    promise: Promise<ApiResult<T>>,
    navigate: NavigateFunction,
    setter: (data: T) => void,
): Promise<ApiResult<T>> {
    promise
        .then((response: ApiResult<T>) => {
            if (response.hasFailed()) {
                const status: number = response.getError().status;
                // TODO: que pasa con el 401? 
                if (status === 404 || status === 403 || status === 500) {
                    navigate(`/error?code=${response.getError().status}`);
                }
            } else {
                setter(response.getData());
            }
        })
        .catch(() => navigate("/error?code=500"))
    return promise;
}