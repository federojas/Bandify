import UserService from "../../services/UserService"
import axios from "axios";
import { application1, application2, correctUserCreateInput, correctUserUpdateInput, passwordRequest, socialMedia1, socialMedia2, user1, user2 } from "../__mocks__";
import UserApi from "../../api/UserApi";
jest.mock("axios");

const userApi = new UserApi(axios)    
const userService = new UserService(userApi);

afterEach(() => {(axios.get as any).mockClear();});
afterEach(() => {(axios.post as any).mockClear();});
afterEach(() => {(axios.put as any).mockClear();});

const axiosGet = (axios.get as any);
const axiosPost = (axios.post as any);
const axiosPut = (axios.put as any);

describe("getUserById()", () => {
    it("should return a user", async () => {

        axiosGet.mockResolvedValueOnce({data: user1});
     
        await userService.getUserById(1).then((response) => {
            expect(response.getData()).toEqual(user1);
            expect(response.hasFailed()).toBeFalsy();
            expect(axiosGet).toHaveBeenCalledTimes(1);
            expect(axiosGet).toHaveBeenCalledWith("/users/1");
        });
    });

    
    it("should return an error", async () => {
        
        axiosGet.mockRejectedValueOnce({response: {data: {message: "error"}}});

        await userService.getUserById(1).then((response) => {
            expect(response.getData()).toBeNull();
            expect(response.hasFailed()).toBeTruthy();
            expect(axiosGet).toHaveBeenCalledTimes(1);
            expect(axiosGet).toHaveBeenCalledWith("/users/1");
        });
    });
});

describe("getUsers()", () => {

    it("should return a list of users", async () => {
        axiosGet.mockResolvedValueOnce({data: [user1, user2]});

        await userService.getUsers().then((response) => {
            expect(response.getData().getContent()).toEqual([user1,user2]);
            expect(response.hasFailed()).toBeFalsy();
            expect(axiosGet).toHaveBeenCalledTimes(1);
            expect(axiosGet).toHaveBeenCalledWith("/users",
            {params: {
                genre: undefined,
                location: undefined,
                order: undefined,
                page: undefined,
                query: undefined,
                role: undefined
            },
            paramsSerializer: {indexes: null}
            }  
            );
        });
    });

    it("should return an error", async () => {
        axiosGet.mockRejectedValueOnce({response: {data: {status: 400, title: "Bad Request"}}});

        await userService.getUsers().then((response) => {
            expect(response.getData()).toBeNull();
            expect(response.hasFailed()).toBeTruthy();
            expect(axiosGet).toHaveBeenCalledTimes(1);
            expect(axiosGet).toHaveBeenCalledWith("/users",
            {params: {
                genre: undefined,
                location: undefined,
                order: undefined,
                page: undefined,
                query: undefined,
                role: undefined
            },
            paramsSerializer: {indexes: null}
            }  
            );
        });
    });
});

describe("createUser()", () => {

    it("should create a user", async () => {
        axiosPost.mockResolvedValueOnce({
            data: correctUserCreateInput,
        });

        await userService.createUser(correctUserCreateInput).then((response) => {
            expect(response.getData()).toEqual({});
            expect(response.hasFailed()).toBeFalsy();
            expect(axiosPost).toHaveBeenCalledTimes(1);
            expect(axiosPost).toHaveBeenCalledWith(
                "/users",
                correctUserCreateInput,
                {
                    headers:{ "Content-Type": "application/vnd.user.v1+json"}
                }
        );
        });
    });

    it("should return an error", async () => {
        axiosPost.mockRejectedValueOnce({response: {data: {message: "error"}}});

        await userService.createUser(correctUserCreateInput).then((response) => {
            expect(response.getData()).toBeNull();
            expect(response.hasFailed()).toBeTruthy();
            expect(axiosPost).toHaveBeenCalledTimes(1);
            expect(axiosPost).toHaveBeenCalledWith(
                "/users",
                correctUserCreateInput,
                {
                    headers:{ "Content-Type": "application/vnd.user.v1+json"}
                }
            );
        });
    });
});

describe("updateUser()", () => {
 

    it("should update a user", async () => {
        axiosPut.mockResolvedValueOnce({
            data: user1,
        });

        await userService.updateUser(1, correctUserUpdateInput).then((response) => {
            expect(response.getData()).toEqual({});
            expect(response.hasFailed()).toBeFalsy();
            expect(axiosPut).toHaveBeenCalledTimes(1);
            expect(axiosPut).toHaveBeenCalledWith(
                "/users/1",
                correctUserUpdateInput
                );
        });
    });

    it("should return an error", async () => {
        axiosPut.mockRejectedValueOnce({response: {data: {status: 400, title: "Bad Request"}}});

        await userService.updateUser(1, correctUserUpdateInput).then((response) => {
            expect(response.getData()).toBeNull();
            expect(response.hasFailed()).toBeTruthy();
            expect(axiosPut).toHaveBeenCalledTimes(1);
            expect(axiosPut).toHaveBeenCalledWith(
                "/users/1",
                correctUserUpdateInput
            );
        });
    });
});

describe("getUserApplications()", () => {
    
        it("should return a list of applications", async () => {
            axiosGet.mockResolvedValueOnce({data: [application1, application2]});
    
            await userService.getUserApplications(1,'PENDING',1).then((response) => {
                
                expect(response.getData().getContent()).toEqual([application1,application2]);
                expect(response.hasFailed()).toBeFalsy();
                expect(axiosGet).toHaveBeenCalledTimes(1);
                expect(axiosGet).toHaveBeenCalledWith("/users/1/applications",
                { params: { state: 'PENDING', page: 1 } }
                );
            });
        });
        
        it("should return an error", async () => {
            axiosGet.mockRejectedValueOnce({response: {data: {status: 400, title: "Bad Request"}}});
    
            await userService.getUserApplications(1,'PENDING',1).then((response) => {
                expect(response.getData()).toBeNull();
                expect(response.hasFailed()).toBeTruthy();
                expect(axiosGet).toHaveBeenCalledTimes(1);
                expect(axiosGet).toHaveBeenCalledWith("/users/1/applications",
                { params: { state: 'PENDING', page: 1 } }
                );
            });
        });
      
});

describe("getUserApplicationsByUrl()", () => {
        
        it("should return a list of applications", async () => {
            axiosGet.mockResolvedValueOnce({data: [application1, application2]});
    
            await userService.getUserApplicationsByUrl("/users/1/applications").then((response) => {
                
                expect(response.getData().getContent()).toEqual([application1,application2]);
                expect(response.hasFailed()).toBeFalsy();
                expect(axiosGet).toHaveBeenCalledTimes(1);
                expect(axiosGet).toHaveBeenCalledWith("/users/1/applications");
            });
        });
        
        it("should return an error", async () => {
            axiosGet.mockRejectedValueOnce({response: {data: {status: 400, title: "Bad Request"}}});
    
            await userService.getUserApplicationsByUrl("/users/1/applications").then((response) => {
                expect(response.getData()).toBeNull();
                expect(response.hasFailed()).toBeTruthy();
                expect(axiosGet).toHaveBeenCalledTimes(1);
                expect(axiosGet).toHaveBeenCalledWith("/users/1/applications");
            });
        });
    
    });

describe("generateUserPassword()", () => {

    
    it("should generate a password", async () => {
        axiosPost.mockResolvedValueOnce({
            data: {
                password: "password"
            },
        });
        
        await userService.generateUserPassword(passwordRequest).then((response) => {
            expect(response.getData()).toEqual(true);
            expect(response.hasFailed()).toBeFalsy();
            expect(axiosPost).toHaveBeenCalledTimes(1);
            expect(axiosPost).toHaveBeenCalledWith(
                "/users/password-tokens",
                passwordRequest,
                {headers: { "Content-Type": "application/vnd.password-token.v1+json" }}
                );
        });
    });

    it("should return an error", async () => {
        axiosPost.mockRejectedValueOnce({response:{data: {status: 400, title: "Bad Request"}}});

        await userService.generateUserPassword(passwordRequest).then((response) => {
            expect(response.getData()).toBeNull();
            expect(response.hasFailed()).toBeTruthy();
            expect(axiosPost).toHaveBeenCalledTimes(1);
            expect(axiosPost).toHaveBeenCalledWith(
                "/users/password-tokens",
                passwordRequest,
                {headers: { "Content-Type": "application/vnd.password-token.v1+json" }}
                );
        });
    });
});

describe("getUserSocialMedia()", () => {
    it("should return a list of social media", async () => {
        axiosGet.mockResolvedValueOnce({data: [socialMedia1, socialMedia2]});

        await userService.getUserSocialMedia(1).then((response) => {
            
            expect(response.getData()).toEqual([socialMedia1,socialMedia2]);
            expect(response.hasFailed()).toBeFalsy();
            expect(axiosGet).toHaveBeenCalledTimes(1);
            expect(axiosGet).toHaveBeenCalledWith("/users/1/social-media");
        });
    });
    
    it("should return an error", async () => {
        axiosGet.mockRejectedValueOnce({response: {data: {status: 400, title: "Bad Request"}}});

        await userService.getUserSocialMedia(1).then((response) => {
            expect(response.getData()).toBeNull();
            expect(response.hasFailed()).toBeTruthy();
            expect(axiosGet).toHaveBeenCalledTimes(1);
            expect(axiosGet).toHaveBeenCalledWith("/users/1/social-media");
        });
    });
});






