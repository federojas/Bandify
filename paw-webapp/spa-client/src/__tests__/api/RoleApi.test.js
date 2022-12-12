import RoleApi from "../../api/RoleApi";

describe("RoleApi", () => {

    test("getRoles", async () => {
        return RoleApi.getRoles().then((roles) => {
            console.log(roles.data)
            expect(roles.data.length).toBeGreaterThan(0);
        });
    });

    test("getRoles by auditionId", async () => {
        return RoleApi.getRoles({auditionId: 1}).then((roles) => {
            console.log(roles.data)
            // expect(genres.data[0].auditionId).toEqual(1);
        });
    })

    test("getRoles by userId", async () => {
        return RoleApi.getRoles({userId: 1}).then((roles) => {
            console.log(roles.data)
            // expect(genres.data[0].auditionId).toEqual(1);
        });
    })
});