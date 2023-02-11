import UserService from "../../services/UserService"
import axios from "axios";
import { application1, application2, correctUserCreateInput, correctUserUpdateInput, passwordRequest, socialMedia1, socialMedia2, user1, user2 } from "../__mocks__";
import UserApi from "../../api/UserApi";
jest.mock("axios");

// jest.mock("../../services/UserService");
// jest.mock("../../api/UserApi");
// jest.mock("../../api/types/ApiResult");

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
            expect(axios.get).toHaveBeenCalledTimes(1);
            expect(axios.get).toHaveBeenCalledWith("/users/1");
        });
    });

    
    it("should return an error", async () => {
        
        axiosGet.mockRejectedValueOnce({response: {data: {message: "error"}}});

        await userService.getUserById(1).then((response) => {
            expect(response.getData()).toBeNull();
            expect(response.hasFailed()).toBeTruthy();
            expect(axios.get).toHaveBeenCalledTimes(1);
            expect(axios.get).toHaveBeenCalledWith("/users/1");
        });
    });
});

describe("getUsers()", () => {

    it("should return a list of users", async () => {
        axiosGet.mockResolvedValueOnce({data: [user1, user2]});

        await userService.getUsers().then((response) => {
            expect(response.getData().getContent()).toEqual([user1,user2]);
            expect(response.hasFailed()).toBeFalsy();
            expect(axios.get).toHaveBeenCalledTimes(1);
            // expect(axios.get).toHaveBeenCalledWith("/users");//TODO: fix this
        });
    });

    it("should return an error", async () => {
        axiosGet.mockRejectedValueOnce({response: {data: {message: "error"}}});

        await userService.getUsers().then((response) => {
            expect(response.getData()).toBeNull();
            expect(response.hasFailed()).toBeTruthy();
            expect(axios.get).toHaveBeenCalledTimes(1);
            // expect(axios.get).toHaveBeenCalledWith("/users");//TODO: fix this
        });
    });
});

describe("createUser()", () => {

    it("should create a user", async () => {
        axiosPost.mockResolvedValueOnce({
            data: correctUserCreateInput,
        });

        await userService.createUser(correctUserCreateInput).then((response) => {
            console.log(response.getData());
            expect(response.getData()).toEqual({});
            expect(response.hasFailed()).toBeFalsy();
            expect(axios.post).toHaveBeenCalledTimes(1);
            expect(axios.post).toHaveBeenCalledWith(
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
            expect(axios.post).toHaveBeenCalledTimes(1);
            expect(axios.post).toHaveBeenCalledWith(
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
            expect(axios.put).toHaveBeenCalledTimes(1);
            expect(axios.put).toHaveBeenCalledWith(
                "/users/1",
                correctUserUpdateInput
                );
        });
    });

    it("should return an error", async () => {
        axiosPut.mockRejectedValueOnce({response: {data: {message: "error"}}});

        await userService.updateUser(1, correctUserUpdateInput).then((response) => {
            expect(response.getData()).toBeNull();
            expect(response.hasFailed()).toBeTruthy();
            expect(axios.put).toHaveBeenCalledTimes(1);
            expect(axios.put).toHaveBeenCalledWith(
                "/users/1",
                correctUserUpdateInput
            );
        });
    });
});

//TODO: fix this
// describe("updateUserProfileImage()", () => {
//     const userApi = new UserApi(axios)    
//     const userService = new UserService(userApi);

//     it("should update a user's profile image", async () => {
//         axiosPut.mockResolvedValueOnce({
//             data: user1,
//         });
        
        

//         await userService.updateUserProfileImage(1,image as any ).then((response) => {
//             console.log(response.getData());
//             expect(response.getData()).toEqual({});
//             expect(response.hasFailed()).toBeFalsy();
//             expect(axios.put).toHaveBeenCalledTimes(1);
//             expect(axios.put).toHaveBeenCalledWith(
//                 "/users/1/profile-image",
//                 "image"
//                 );
//         });
//     });

//     it("should return an error", async () => {
//         axiosPut.mockClear();
//         axiosPut.mockRejectedValueOnce({response: {data: {message: "error"}}});

//         await userService.updateUserProfileImage(1, "image").then((response) => {
//             expect(response.getData()).toBeNull();
//             expect(response.hasFailed()).toBeTruthy();
//             expect(axios.put).toHaveBeenCalledTimes(1);
//             expect(axios.put).toHaveBeenCalledWith(
//                 "/users/1/profile-image",
//                 "image"
//             );
//         });
//     });
// });



describe("getUserApplications()", () => {
    
        it("should return a list of applications", async () => {
            axiosGet.mockResolvedValueOnce({data: [application1, application2]});
    
            await userService.getUserApplications(1,'PENDING',1).then((response) => {
                
                expect(response.getData().getContent()).toEqual([application1,application2]);
                expect(response.hasFailed()).toBeFalsy();
                expect(axios.get).toHaveBeenCalledTimes(1);
                expect(axios.get).toHaveBeenCalledWith("/users/1/applications",
                { params: { state: 'PENDING', page: 1 } }
                );
            });
        });
        
        it("should return an error", async () => {
            axiosGet.mockRejectedValueOnce({response: {data: {message: "error"}}});
    
            await userService.getUserApplications(1,'PENDING',1).then((response) => {
                expect(response.getData()).toBeNull();
                expect(response.hasFailed()).toBeTruthy();
                expect(axios.get).toHaveBeenCalledTimes(1);
                expect(axios.get).toHaveBeenCalledWith("/users/1/applications",
                { params: { state: 'PENDING', page: 1 } }
                );
            });
        });
      
});

describe("getUserApplicationsByUrl()", () => { //todo: check this
        
        it("should return a list of applications", async () => {
            axiosGet.mockResolvedValueOnce({data: [application1, application2]});
    
            await userService.getUserApplicationsByUrl("/users/1/applications").then((response) => {
                
                expect(response.getData().getContent()).toEqual([application1,application2]);
                expect(response.hasFailed()).toBeFalsy();
                expect(axios.get).toHaveBeenCalledTimes(1);
                expect(axios.get).toHaveBeenCalledWith("/users/1/applications");
            });
        });
        
        it("should return an error", async () => {
            axiosGet.mockRejectedValueOnce({response: {data: {message: "error"}}});
    
            await userService.getUserApplicationsByUrl("/users/1/applications").then((response) => {
                expect(response.getData()).toBeNull();
                expect(response.hasFailed()).toBeTruthy();
                expect(axios.get).toHaveBeenCalledTimes(1);
                expect(axios.get).toHaveBeenCalledWith("/users/1/applications");
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
            expect(axios.post).toHaveBeenCalledTimes(1);
            expect(axios.post).toHaveBeenCalledWith(
                "/users/password-tokens",
                passwordRequest,
                {headers: { "Content-Type": "application/vnd.password-token.v1+json" }}
                );
        });
    });

    it("should return an error", async () => {
        axiosPost.mockRejectedValueOnce({response: {data: {message: "error"}}});

        await userService.generateUserPassword(passwordRequest).then((response) => {
            expect(response.getData()).toBeNull();
            expect(response.hasFailed()).toBeTruthy();
            expect(axios.post).toHaveBeenCalledTimes(1);
            expect(axios.post).toHaveBeenCalledWith(
                "/users/password-tokens",
                passwordRequest,
                {headers: { "Content-Type": "application/vnd.password-token.v1+json" }}
                );
        });
    });
});
//todo: fix this
// describe("verifyUser()", () => {
//     it("should verify a user", async () => {
//         (axios.post as any).mockClear();
//         (axios.post as any).mockResolvedValueOnce({
//             data: {
//                 message: "success"
//             },
//         });
        
//         await userService.verifyUser("token").then((response) => {
//             expect(response.getData()).toEqual(true);
//             expect(response.hasFailed()).toBeFalsy();
//             expect(axios.post).toHaveBeenCalledTimes(1);
//             expect(axios.post).toHaveBeenCalledWith(
//                 "/users/1/verify",
//                 {},
//                 {headers: { "Content-Type": "application/vnd.verify-user.v1+json" }}
//                 );
//         });
//     })
// });

describe("getUserSocialMedia()", () => {
    it("should return a list of social media", async () => {
        axiosGet.mockResolvedValueOnce({data: [socialMedia1, socialMedia2]});

        await userService.getUserSocialMedia(1).then((response) => {
            
            expect(response.getData()).toEqual([socialMedia1,socialMedia2]);
            expect(response.hasFailed()).toBeFalsy();
            expect(axios.get).toHaveBeenCalledTimes(1);
            expect(axios.get).toHaveBeenCalledWith("/users/1/social-media");
        });
    });
    
    it("should return an error", async () => {
        axiosGet.mockRejectedValueOnce({response: {data: {message: "error"}}});

        await userService.getUserSocialMedia(1).then((response) => {
            expect(response.getData()).toBeNull();
            expect(response.hasFailed()).toBeTruthy();
            expect(axios.get).toHaveBeenCalledTimes(1);
            expect(axios.get).toHaveBeenCalledWith("/users/1/social-media");
        });
    });
});






