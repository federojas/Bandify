import axios from "axios";
import MembershipApi from "../../api/MembershipApi";
import UserApi from "../../api/UserApi";
import MembershipService from "../../services/MembershipService";
import {  artist, artist2, audition1, band, band2, membership1, membership1EditParams, membership2, membership3, membershipPostParams, postResponse } from "../__mocks__";
jest.mock("axios");


const userApi = new UserApi(axios);
const membershipApi = new MembershipApi(axios);

const membershipService = new MembershipService(membershipApi, userApi);

afterEach(() => {(axios.get as any).mockClear();});
afterEach(() => {(axios.post as any).mockClear();});
afterEach(() => {(axios.put as any).mockClear();});
afterEach(() => {(axios.delete as any).mockClear();});

const axiosGet = (axios.get as any);
const axiosPost = (axios.post as any);
const axiosPut = (axios.put as any);
const axiosDelete = (axios.delete as any);

describe("getMembershipById()", () => {
    it("should return a membership", async () => {
        axiosGet.mockResolvedValueOnce({data: membership1});
        axiosGet.mockResolvedValueOnce({data: artist});
        axiosGet.mockResolvedValueOnce({data: band});
    
        await membershipService.getMembershipById(1).then((response) => {
            expect(response.getData()).toEqual(membership1);
            expect(response.getData().artist).toEqual(artist);
            expect(response.getData().band).toEqual(band);
            expect(response.hasFailed()).toEqual(false);
            expect(axiosGet).toHaveBeenCalledTimes(3);
            expect(axiosGet).toHaveBeenCalledWith("/memberships/1",
                {headers: {"Accept": "application/vnd.membership.v1+json" }}
            );
        });
    });
    
    it("should return an error", async () => {
        axiosGet.mockRejectedValueOnce({response: {data: "error"}});
        await membershipService.getMembershipById(1).then((response) => {
            expect(response.hasFailed()).toEqual(true);
            expect(axiosGet).toHaveBeenCalledTimes(1);
        });
    });
    
});

describe("getUserMemberships()", () => {
    it("should return a list of memberships", async () => {
        axiosGet.mockResolvedValueOnce({data: [membership1, membership2]});
        axiosGet.mockResolvedValueOnce({data: artist});
        axiosGet.mockResolvedValueOnce({data: band});
        axiosGet.mockResolvedValueOnce({data: artist});
        axiosGet.mockResolvedValueOnce({data: band2});
    
        await membershipService.getUserMemberships({user: 1}, 1).then((response) => {
            expect(response.getData().getContent()).toEqual([membership1, membership2]);
            expect(response.getData().getContent()[0].artist).toEqual(artist);
            expect(response.getData().getContent()[0].band).toEqual(band);
            expect(response.getData().getContent()[1].artist).toEqual(artist);
            expect(response.getData().getContent()[1].band).toEqual(band2);
            expect(response.hasFailed()).toEqual(false);
            expect(axiosGet).toHaveBeenCalledTimes(5);
            expect(axiosGet).toHaveBeenCalledWith("/memberships",
                {headers: {"Accept": "application/vnd.membership-list.v1+json" },
                 params: {user: 1, preview: undefined, state: undefined, page: 1},
                 paramsSerializer:{indexes: null}
                }
            );
        });
    });
    
    it("should return an error", async () => {
        axiosGet.mockRejectedValueOnce({response: {data: "error"}});
        await membershipService.getUserMemberships({user: 1}, 1).then((response) => {
            expect(response.hasFailed()).toEqual(true);
            expect(axiosGet).toHaveBeenCalledTimes(1);
        });
    });
    
});

describe("getUserMembershipsByBand()", () => {
    it("should return a list of memberships", async () => {
        axiosGet.mockResolvedValueOnce({data: [membership1, membership3]});
        axiosGet.mockResolvedValueOnce({data: artist});
        axiosGet.mockResolvedValueOnce({data: band});
        axiosGet.mockResolvedValueOnce({data: artist2});
        axiosGet.mockResolvedValueOnce({data: band});
    
        await membershipService.getUserMembershipsByBand(1, 2).then((response) => {
            expect(response.getData().getContent()).toEqual([membership1, membership3]);
            expect(response.getData().getContent()[0].artist).toEqual(artist);
            expect(response.getData().getContent()[0].band).toEqual(band);
            expect(response.getData().getContent()[1].artist).toEqual(artist2);
            expect(response.getData().getContent()[1].band).toEqual(band);
            expect(response.hasFailed()).toEqual(false);
            expect(axiosGet).toHaveBeenCalledTimes(5);
            expect(axiosGet).toHaveBeenCalledWith("/memberships",
                {
                 params: {user: 1, band: 2}
                }
            );
        });
    });
    
    it("should return an error", async () => {
        axiosGet.mockRejectedValueOnce({response: {data: {status: 400, title: "Bad Request"}}});
        await membershipService.getUserMembershipsByBand(1, 2).then((response) => {
            expect(response.hasFailed()).toEqual(true);
            expect(axiosGet).toHaveBeenCalledTimes(1);
        });
    });
    
});

describe("inviteToBand()", () => {
    it("should return a PostResponse", async () => {
        axiosPost.mockResolvedValueOnce({data: postResponse,  headers: {location: "http://localhost:8080/memberships/1"}});
        axiosGet.mockResolvedValueOnce({data: artist});
        axiosGet.mockResolvedValueOnce({data: band});
    
        await membershipService.inviteToBand(membershipPostParams).then((response) => {
            expect(response.getData()).toEqual(postResponse);
            expect(response.hasFailed()).toEqual(false);
            expect(axiosPost).toHaveBeenCalledTimes(1);
            expect(axiosPost).toHaveBeenCalledWith("/memberships?user=1",
            {roles:["baterista", "guitarrista"],
            description: "description"},
            {   headers: {"Accept": "application/vnd.membership.v1+json", "Content-Type": "application/vnd.membership.v1+json"}}
            );
        });
    });
    
    it("should return an error", async () => {
        axiosPost.mockRejectedValueOnce({response: {data: "error"}});
        await membershipService.inviteToBand(membershipPostParams).then((response) => {
            expect(response.hasFailed()).toEqual(true);
            expect(axiosPost).toHaveBeenCalledTimes(1);
        });
    });
    
});

describe("createMembershipByApplication()", () => {
    it("should return a PostResponse", async () => {
        axiosPost.mockResolvedValueOnce({data: postResponse,  headers: {location: "http://localhost:8080/memberships/1"}});
    
        await membershipService.createMembershipByApplication(membershipPostParams,audition1.id).then((response) => {
            expect(response.getData()).toEqual(postResponse);
            expect(response.hasFailed()).toEqual(false);
            expect(axiosPost).toHaveBeenCalledTimes(1);
        });
    });

    it("should return an error", async () => {
        axiosPost.mockRejectedValueOnce({response: {data: {status: 400, title: "Bad Request"}}});
        await membershipService.createMembershipByApplication(membershipPostParams,audition1.id).then((response) => {
            expect(response.hasFailed()).toEqual(true);
            expect(axiosPost).toHaveBeenCalledTimes(1);
        });
    });
});

describe("accept()", () => {
    it("should return a PostResponse", async () => {
        axiosPut.mockResolvedValueOnce({data: postResponse});
    
        await membershipService.accept(membership1).then((response) => {
            expect(response.getData()).toEqual(null);
            expect(response.hasFailed()).toEqual(false);
            expect(axiosPut).toHaveBeenCalledTimes(1);
            expect(axiosPut).toHaveBeenCalledWith("/memberships/1",
            {description: "membership description", roles: ["baterista", "guitarrista"], state: "ACCEPTED"},
            {headers: {"Content-Type": "application/vnd.membership.v1+json"}}
            );
        });
    });
    
    it("should return an error", async () => {
        axiosPut.mockRejectedValueOnce({response: {data: {status: 400, title: "Bad Request"}}});
        await membershipService.accept(membership1).then((response) => {
            expect(response.hasFailed()).toEqual(true);
            expect(axiosPut).toHaveBeenCalledTimes(1);
        });
    });
    
});

describe("reject()", () => {
    it("should return a PostResponse", async () => {
        axiosPut.mockResolvedValueOnce({data: postResponse});
    
        await membershipService.reject(membership1).then((response) => {
            expect(response.getData()).toEqual(null);
            expect(response.hasFailed()).toEqual(false);
            expect(axiosPut).toHaveBeenCalledTimes(1);
            expect(axiosPut).toHaveBeenCalledWith("/memberships/1",
            {description: "membership description", roles: ["baterista", "guitarrista"], state: "REJECTED"},
            {headers: {"Content-Type": "application/vnd.membership.v1+json"}}
            );
        });
    });
    
    it("should return an error", async () => {
        axiosPut.mockRejectedValueOnce({response: {data: {status: 400, title: "Bad Request"}}});
        await membershipService.reject(membership1).then((response) => {
            expect(response.hasFailed()).toEqual(true);
            expect(axiosPut).toHaveBeenCalledTimes(1);
        });
    });
    
});

describe("edit()", () => {
    it("should return a PostResponse", async () => {
        axiosPut.mockResolvedValueOnce({data: postResponse});
    
        await membershipService.edit(membership1EditParams, membership1.id).then((response) => {
            expect(response.getData()).toEqual(null);
            expect(response.hasFailed()).toEqual(false);
            expect(axiosPut).toHaveBeenCalledTimes(1);
            expect(axiosPut).toHaveBeenCalledWith("/memberships/1",
            {description: membership1EditParams.description, roles: membership1EditParams.roles, state: "ACCEPTED"},
            {headers: {"Content-Type": "application/vnd.membership.v1+json"}}
            );
        });
    });
    
    it("should return an error", async () => {
        axiosPut.mockRejectedValueOnce({response: {data: "error"}});
        await membershipService.edit(membership1EditParams, membership1.id).then((response) => {
            expect(response.hasFailed()).toEqual(true);
            expect(axiosPut).toHaveBeenCalledTimes(1);
        });
    });
    
});

describe("kickMember()", () => {
    it("should return a PostResponse", async () => {
        axiosDelete.mockResolvedValueOnce({data: postResponse});
    
        await membershipService.kickMember(membership1.id).then((response) => {
            expect(response.getData()).toEqual(null);
            expect(response.hasFailed()).toEqual(false);
            expect(axiosDelete).toHaveBeenCalledTimes(1);
            expect(axiosDelete).toHaveBeenCalledWith("/memberships/1",
            {headers: {"Accept": "application/vnd.membership.v1+json"}});
        });
    });
    
    it("should return an error", async () => {
        axiosDelete.mockRejectedValueOnce({response: {data: {status: 400, title: "Bad Request"}}});
        await membershipService.kickMember(membership1.id).then((response) => {
            expect(response.hasFailed()).toEqual(true);
            expect(axiosDelete).toHaveBeenCalledTimes(1);
        });
    });
    
});