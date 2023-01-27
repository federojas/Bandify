import {locationApi} from "../api";
import {Location} from "../models";
import {ErrorService} from "./ErrorService";
import ApiResult from "../api/types/ApiResult";

export class LocationService {
    public async getLocations(): Promise<ApiResult<Location[]>>{
        try {
            const response = await locationApi.getLocations();
            return new ApiResult(
                response.map(r => { const location: Location = {id: r.id, name: r.locName}; return location }),
                false,
                null as any);
        } catch (error) {
            return ErrorService.returnApiError(error);
        }
    }


    public async getLocationById(id : number): Promise<ApiResult<Location>> {
        try {
            const current = await locationApi.getLocationById(id);
            return new ApiResult(
                {id:current.id, name:current.locName} as Location,
                false,
                null as any
            );
        } catch (error) {
            return ErrorService.returnApiError(error);
        }
    }
}