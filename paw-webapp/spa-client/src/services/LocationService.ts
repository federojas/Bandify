import LocationApiTest from "../api/LocationApi";
import ApiResult from "../api/types/ApiResult";
import { Location } from "../models";
import {ErrorService} from "./ErrorService";

export default class LocationService {

    private locationApi: LocationApiTest;

    constructor(locationApi: LocationApiTest) {
        this.locationApi = locationApi;
    }
    public async getLocations(): Promise<ApiResult<Location[]>> {
        try {
            const response = await this.locationApi.getLocations();
            return new ApiResult(
                response.map(r => { const location: Location = {id: r.id, name: r.locName}; return location }),
                false,
                null as any);
        } catch (error) {
            return ErrorService.returnApiError(error);
        }
    }


    public async getLocationById(id: number): Promise<ApiResult<Location>> {
        try {
            const current = await this.locationApi.getLocationById(id);
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