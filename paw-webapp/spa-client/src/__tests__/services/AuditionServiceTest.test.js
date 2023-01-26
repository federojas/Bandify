import {auditionService} from "../../services";

//TODO: TESTS

describe("getAuditionById()", () => {
    it("should return an audition", async () => {

        const aud = await auditionService.getAuditionById(3);
        console.log(aud);
    });
});

describe("getAuditions()", () => {
    it("should return an audition list", async () => {

        const aud = await auditionService.getAuditions(1, "NUEVAA");
        console.log(aud);
    });
});