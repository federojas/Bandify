import GenreApi from "../../api/GenreApi";

// Import and mock the api module
import api from "../../api/api";
jest.mock("../../api/api");

const mockData = [
    { id: 1, genreName: "Rock", self: "http://example.com/genres/1" },
    { id: 2, genreName: "Hip Hop", self: "http://example.com/genres/2" },
    { id: 3, genreName: "Jazz", self: "http://example.com/genres/3" },
  ]

describe("getGenres()", () => {
  it("should return a list of genres", async () => {
    // Mock the API call to return a list of genres
    api.get.mockResolvedValue({
      data: mockData,
    });

    // Call the getGenres() function
    const genres = await GenreApi.getGenres();
    // Verify that the genres were returned correctly
    expect(genres).toEqual(mockData);
  });
});

describe("getGenres() with params", () => {
    it("should return a list of genres", async () => {
      // Mock the API call to return a list of genres
      api.get.mockResolvedValue({
        data: mockData,
      });
  
      // Call the getGenres() function with a params object
      const genres = await GenreApi.getGenres({ auditionId: 123, userId: 456 });
  
      // Verify that the genres were returned correctly
      expect(genres).toEqual(mockData);
  
      // Verify that the API was called with the correct parameters
      expect(api.get).toHaveBeenCalledWith(
        "/genres",
        expect.objectContaining({
          params: {
            audition: 123,
            user: 456,
          },
        })
      );
    });
  });
  

describe("getGenreById()", () => {
  it("should return a genre by its ID", async () => {
    // Mock the API call to return a single genre
    api.get.mockResolvedValue({
      data: mockData[0],
    });

    // Call the getGenreById() function
    const genre = await GenreApi.getGenreById(1);

    // Verify that the genre was returned correctly
    expect(genre).toEqual(mockData[0]);
  });
});
