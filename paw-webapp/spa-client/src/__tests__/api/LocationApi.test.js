import LocationApi from "../../api/LocationApi";

describe("LocationApi", () => {

    test("getLocations", async () => {
        return LocationApi.getLocations().then((locations) => {
            console.log(locations.data)
            expect(locations.data.length).toBeGreaterThan(0);
        });
    });

    test("getLocations by auditionId", async () => {
        return LocationApi.getLocations({auditionId: 1}).then((locations) => {
            console.log(locations.data)
            // expect(genres.data[0].auditionId).toEqual(1);
        });
    })

    // test("getLocations by userId", async () => {
    //     return LocationApi.getLocations({userId: 1}).then((locations) => {
    //         console.log(locations.data)
    //         // expect(genres.data[0].auditionIdq).toEqual(1);
    //     });
    // })
});