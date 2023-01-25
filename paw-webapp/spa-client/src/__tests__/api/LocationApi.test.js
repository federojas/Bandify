import {locationApi} from "../../api";


//TODO: TESTS


describe("getLocations()", () => {
  it("should return a list of locations", async () => {
    // Call the getLocations() function
    const locations = await locationApi.getLocations();
    console.log(locations);
  });
});

describe("getLocations() with params", () => {
  it("should return a list of locations", async () => {

    // Call the getLocations() function with a params object
    const locations = await locationApi.getLocations({ auditionId: 123 });
    console.log(locations)
  });
});

describe("getGenreById()", () => {
    it("should return a location by its ID", async () => {
        // Call the getGenreById() function
        const location = await locationApi.getLocationById(1);
        console.log(location);
    });
})
