import axios from "axios";
import LocationApi from "../../api/LocationApi";
import LocationService from "../../services/LocationService";
import { location1, location1Response, location2, location2Response } from "../__mocks__";


jest.mock("axios");

afterEach(() => {(axios.get as any).mockClear();});
afterEach(() => {(axios.post as any).mockClear();});
afterEach(() => {(axios.put as any).mockClear();});
afterEach(() => {(axios.delete as any).mockClear();});

const axiosGet = (axios.get as any);

const locationApi = new LocationApi(axios);
const locationService = new LocationService(locationApi);

describe("getLocations()", () => {
    it("should return locations", async () => {
        axiosGet.mockResolvedValue({ data: [location1Response, location2Response] });
        await locationService.getLocations().then((result) => {
            expect(result.getData()).toEqual([location1, location2]);
            expect(axiosGet).toHaveBeenCalledTimes(1);
            expect(axiosGet).toHaveBeenCalledWith("/locations",
            {params:{audition:undefined,user:undefined}});
        });
    });

    it("should return error", async () => {
        axiosGet.mockRejectedValue({response: {data: {status: 400, title: "Bad Request"}}});
        await locationService.getLocations().then((result) => {
            expect(result.hasFailed()).toEqual(true);
            expect(axiosGet).toHaveBeenCalledTimes(1);
        });
    });
});

describe("getLocationById()", () => {
    it("should return location", async () => {
        axiosGet.mockResolvedValue({ data: location1Response });
        await locationService.getLocationById(1).then((result) => {
            expect(result.getData()).toEqual(location1);
            expect(axiosGet).toHaveBeenCalledTimes(1);
            expect(axiosGet).toHaveBeenCalledWith("/locations/1");
        });
    });

    it("should return error", async () => {
        axiosGet.mockRejectedValue({response: {data: {status: 400, title: "Bad Request"}}});
        await locationService.getLocationById(1).then((result) => {
            expect(result.hasFailed()).toEqual(true);
            expect(axiosGet).toHaveBeenCalledTimes(1);
        });
    });
});