import { roleApi } from "../../api";


//TODO: TESTS

describe("getRoles()", () => {
    it("should return a list of roles", async () => {
        // Call the getRoles() function
        const roles = await roleApi.getRoles();
        console.log(roles);
    });
})

describe("getRoles() with params", () => {
    it("should return a list of roles", async () => {

        // Call the getRoles() function with a params object
        const roles = await roleApi.getRoles({ auditionId: 3 });
        console.log(roles);
    });
})

describe("getRoleById()", () => {
    it("should return a role by its ID", async () => {
        // Call the getRoleById() function
        const role = await roleApi.getRoleById(1);
        console.log(role);
    });
});
