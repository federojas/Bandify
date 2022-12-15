import AuditionApi from "../../api/AuditionApi";

// import api from "../../api/api";
// jest.mock("../../api/api");

describe("createAudition()", () => {
    it("should call api.post() with the correct parameters", async () => {
        const audition = {
            title: "Test Audition",
            description: "Test Audition Description",
            location: "Test Audition Location",
            musicGenres: ["Test Audition Music Genre"],
            lookingFor: ["Test Audition Looking For"],
        }     

        await AuditionApi.createAudition(audition);

    });
})