import axios from "axios";
import RoleApi from "../../api/RoleApi";
import RoleService from "../../services/RoleService";
import { role1, role1Response, role2, role2Response } from "../__mocks__";
jest.mock("axios");

const roleApi = new RoleApi(axios);
const roleService = new RoleService(roleApi);

afterEach(() => {(axios.get as any).mockClear();});
afterEach(() => {(axios.post as any).mockClear();});
afterEach(() => {(axios.put as any).mockClear();});
afterEach(() => {(axios.delete as any).mockClear();});

const axiosGet = (axios.get as any);
const axiosPost = (axios.post as any);
const axiosPut = (axios.put as any);
const axiosDelete = (axios.delete as any);

describe("getRoles()", () => {
    it("should return roles", async () => {
        axiosGet.mockResolvedValue({ data: [role1Response, role2Response] });
        await roleService.getRoles().then((result) => {
            expect(result.getData()).toEqual([role1, role2]);
            expect(axiosGet).toHaveBeenCalledTimes(1);
            expect(axiosGet).toHaveBeenCalledWith("/roles",
            {
                headers: { Accept: "application/vnd.role-list.v1+json"},
                params:{audition:undefined,user:undefined, membership:undefined}
            });
        });
    });

    it("should return error", async () => {
        axiosGet.mockRejectedValue({response: { data: { error: "error" } }});
        await roleService.getRoles().then((result) => {
            expect(result.hasFailed()).toEqual(true);
            expect(axiosGet).toHaveBeenCalledTimes(1);
        });
    });
});

describe("getRoleById()", () => {
    it("should return role", async () => {
        axiosGet.mockResolvedValue({ data: role1Response });
        await roleService.getRoleById(1).then((result) => {
            expect(result.getData()).toEqual(role1);
            expect(axiosGet).toHaveBeenCalledTimes(1);
            expect(axiosGet).toHaveBeenCalledWith("/roles/1");
        });
    });

    it("should return error", async () => {
        axiosGet.mockRejectedValue({response: { data: { error: "error" } }});
        await roleService.getRoleById(1).then((result) => {
            expect(result.hasFailed()).toEqual(true);
            expect(axiosGet).toHaveBeenCalledTimes(1);
        });
    });
});
