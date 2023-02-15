import { NavigateFunction } from "react-router-dom";
import ApiResult from "../api/types/ApiResult";
import { Location } from "react-router-dom";

export async function serviceCall<T>(
    promise: Promise<ApiResult<T>>,
    navigate: NavigateFunction,
    setter?: (data: T) => void,
    location?: Location,
    
): Promise<ApiResult<T>> {
    promise
        .then((response: ApiResult<T>) => {
            if (response.hasFailed()) {
                const status: number = response.getError().status;
                if (status === 404 || status === 403 || status === 500) {
                    navigate(`/error?code=${response.getError().status}`);
                } else if(status === 401) {
                    navigate('/login', { state: { from: location }, replace: true });
                }
            } else {
                if(setter)
                    setter(response.getData());
            }
        })
        .catch(() => navigate("/error?code=500"))
    return promise;
}