import {auditionApi} from "../../api";

//TODO: TESTS


describe("createAudition()", () => {
    it("should call api.post() with the correct parameters", async () => {
        const audition = {
            title: "Test Audition",
            description: "Test Audition Description",
            location: "Test Audition Location",
            musicGenres: ["Test Audition Music Genre"],
            lookingFor: ["Test Audition Looking For"],
        }     

        await auditionApi.createAudition(audition);
    });
})

describe("getAuditions()", () => {
    it("should return an audition", async () => {

        const aud = await auditionApi.getAuditionById(3);
        console.log(aud);
    });
});

describe("getAuditionById()", () => {
    it("should return an audition list", async () => {

        const aud = await auditionApi.getAuditions(1, "NUEVAAAAAAAAAAAa");
        console.log(aud);
    });
});