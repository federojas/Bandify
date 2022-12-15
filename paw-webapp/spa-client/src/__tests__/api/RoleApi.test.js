import RoleApi from "../../api/RoleApi";

import api from "../../api/Api";
jest.mock("../../api/Api");

const mockData = [
    { id: 1, roleName: "Singer", self: "http://example.com/roles/1" },
    { id: 2, roleName: "Pianist", self: "http://example.com/roles/2" },
    { id: 3, roleName: "Drummer", self: "http://example.com/roles/3" },
    { id: 4, roleName: "Bassist", self: "http://example.com/roles/4" },
]

describe("getRoles()", () => {
    it("should return a list of roles", async () => {
        // Mock the API call to return a list of roles
        api.get.mockResolvedValue({
            data: mockData,
        });

        // Call the getRoles() function
        const roles = await RoleApi.getRoles();
        // Verify that the roles were returned correctly
        expect(roles).toEqual(mockData);
    });
})

describe("getRoles() with params", () => {
    it("should return a list of roles", async () => {
        // Mock the API call to return a list of roles
        api.get.mockResolvedValue({
            data: mockData,
        });

        // Call the getRoles() function with a params object
        const roles = await RoleApi.getRoles({ auditionId: 123 });

        // Verify that the roles were returned correctly
        expect(roles).toEqual(mockData);

        // Verify that the API was called with the correct parameters
        expect(api.get).toHaveBeenCalledWith(
            "/roles",
            expect.objectContaining({
                params: {
                    audition: 123,
                },
            })
        );
    });
})

describe("getRoleById()", () => {
    it("should return a role by its ID", async () => {
        // Mock the API call to return a single role
        api.get.mockResolvedValue({
            data: mockData[0],
        });

        // Call the getRoleById() function
        const role = await RoleApi.getRoleById(1);

        // Verify that the role was returned correctly
        expect(role).toEqual(mockData[0]);

        // Verify that the API was called with the correct parameters
        expect(api.get).toHaveBeenCalledWith("/roles/1");
    });
});
