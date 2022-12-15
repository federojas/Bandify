import LocationApi from "../../api/LocationApi";
import AuditionApi from "../../api/AuditionApi";
import api from "../../api/api";
jest.mock("../../api/api");

const mockData = [
  { id: 1, locName: "CABA", self: "http://example.com/locations/1" },
  { id: 2, locName: "GBA", self: "http://example.com/locations/2" },
  { id: 3, locName: "Cordoba", self: "http://example.com/locations/3" },
  { id: 4, locName: "Mendoza", self: "http://example.com/locations/4" },
  { id: 5, locName: "Salta", self: "http://example.com/locations/5" },
];

describe("getLocations()", () => {
  it("should return a list of locations", async () => {
    // Mock the API call to return a list of locations
    api.get.mockResolvedValue({
      data: mockData,
    });

    // Call the getLocations() function
    const locations = await LocationApi.getLocations();
    // Verify that the locations were returned correctly
    expect(locations).toEqual(mockData);
  });
});

describe("getLocations() with params", () => {
  it("should return a list of locations", async () => {
    // Mock the API call to return a list of locations
    api.get.mockResolvedValue({
      data: mockData,
    });

    // Call the getLocations() function with a params object
    const locations = await LocationApi.getLocations({ auditionId: 123 });

    // Verify that the locations were returned correctly
    expect(locations).toEqual(mockData);

    // Verify that the API was called with the correct parameters
    expect(api.get).toHaveBeenCalledWith(
      "/locations",
      expect.objectContaining({
        params: {
          audition: 123,
        },
      })
    );
  });
});

describe("getGenreById()", () => {
    it("should return a location by its ID", async () => {
        // Mock the API call to return a single location
        api.get.mockResolvedValue({
        data: mockData[0],
        });
    
        // Call the getGenreById() function
        const location = await LocationApi.getLocationById(1);
    
        // Verify that the location was returned correctly
        expect(location).toEqual(mockData[0]);
    });
})
