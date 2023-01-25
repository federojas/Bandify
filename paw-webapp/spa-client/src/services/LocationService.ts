import {locationApi} from "../api";
import {Location} from "../models";

export class LocationService {
    public async getLocations(): Promise<Location[] | undefined>{
        try {
            const response = await locationApi.getLocations();
            return response.map(r => { const location: Location = {id: r.id, name: r.locName}; return location });
        } catch (error) {
            console.log("error location");
        }
    }


    public async getLocationById(id : number): Promise<Location | undefined> {
        try {
            const current = await locationApi.getLocationById(id);
            return {id:current.id, name:current.locName} as Location;
        } catch (error) {
            console.log("error location");
        }
    }
}