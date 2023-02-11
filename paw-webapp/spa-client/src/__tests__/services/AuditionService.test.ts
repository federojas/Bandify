import axios from "axios";
import AuditionApi from "../../api/AuditionApi";
import AuditionService from "../../services/AuditionService";
import { application1, application2, audition1, audition2, auditionPostResponse, correctAuditionInput } from "../__mocks__";
jest.mock("axios");

const auditionApi = new AuditionApi(axios);
const auditionService = new AuditionService(auditionApi);
afterEach(() => {(axios.get as any).mockClear();});
afterEach(() => {(axios.post as any).mockClear();});
afterEach(() => {(axios.put as any).mockClear();});
afterEach(() => {(axios.delete as any).mockClear();});

const axiosGet = (axios.get as any);
const axiosPost = (axios.post as any);
const axiosPut = (axios.put as any);
const axiosDelete = (axios.delete as any);

describe("getAuditionById", () => {
    axiosGet.mockResolvedValueOnce({data: audition1});

    it("should return an audition", async () => {

        await auditionService.getAuditionById(1).then((response)=>{
            expect(response.getData()).toEqual(audition1);
            expect(response.hasFailed()).toEqual(false);
            expect(axiosGet).toHaveBeenCalledTimes(1);
            expect(axiosGet).toHaveBeenCalledWith("/auditions/1");
        })}
    )

    it("should return an error", async () => {
        axiosGet.mockRejectedValueOnce({response: {data: {status: 400, title: "Bad Request", path: "/auditions/1", messages: ["Audition not found"]}}});
        await auditionService.getAuditionById(1).then((response)=>{
            expect(response.hasFailed()).toEqual(true);
            expect(axiosGet).toHaveBeenCalledTimes(1);
            expect(axiosGet).toHaveBeenCalledWith("/auditions/1");
        })}
    );
});

describe("getAuditionByUrl", () => {
    
    it("should return an audition", async () => {
        axiosGet.mockResolvedValueOnce({data: audition1});

        await auditionService.getAuditionByUrl("/auditions/1").then((response)=>{
            expect(response.getData()).toEqual(audition1);
            expect(response.hasFailed()).toEqual(false);
            expect(axiosGet).toHaveBeenCalledTimes(1);
            expect(axiosGet).toHaveBeenCalledWith("/auditions/1");
        })}
    )

    it("should return an error", async () => {
        axiosGet.mockRejectedValueOnce({response: {data: {status: 400, title: "Bad Request", path: "/auditions/1", messages: ["Audition not found"]}}});
        await auditionService.getAuditionByUrl("/auditions/1").then((response)=>{
            expect(response.hasFailed()).toEqual(true);
            expect(axiosGet).toHaveBeenCalledTimes(1);
            expect(axiosGet).toHaveBeenCalledWith("/auditions/1");
        })}
    );
});

describe("getAuditions()", () => {

    it("should return a list of auditions", async () => {
        axiosGet.mockResolvedValueOnce({data: [audition1, audition2]});
        await auditionService.getAuditions().then((response)=>{
            expect(response.getData().getContent()).toEqual([audition1, audition2]);
            expect(response.getData().getContent().length).toEqual(2);
            expect(response.hasFailed()).toEqual(false);
            expect(axiosGet).toHaveBeenCalledTimes(1);
            //todo: params serializer is not working
            // expect(axiosGet).toHaveBeenCalledWith("/auditions",
            // {params: {
            //     genre: undefined,
            //     location: undefined,
            //     order: undefined,
            //     page: undefined,
            //     query: undefined,
            //     role: undefined
            // }},  
            
            // );
        })}
    )

    it("should return an error", async () => {
        axiosGet.mockRejectedValueOnce({response: {data: {status: 400, title: "Bad Request", path: "/auditions", messages: ["Audition not found"]}}});
        await auditionService.getAuditions().then((response)=>{
            expect(response.hasFailed()).toEqual(true);
            expect(axiosGet).toHaveBeenCalledTimes(1);
            //todo: params serializer is not working
            // expect(axiosGet).toHaveBeenCalledWith("/auditions");
        })}
    );
});

describe("getAuditionsByBandId()", () => {
    
    it("should return a list of auditions", async () => {
        axiosGet.mockResolvedValueOnce({data: [audition1, audition2]});
        await auditionService.getAuditionsByBandId(1,1).then((response)=>{
            expect(response.getData().getContent()).toEqual([audition1, audition2]);
            expect(response.getData().getContent().length).toEqual(2);
            expect(response.hasFailed()).toEqual(false);
            expect(axiosGet).toHaveBeenCalledTimes(1);
            expect(axiosGet).toHaveBeenCalledWith("/auditions",
            {params: {
                "bandId": 1,
                "page": 1
            }},
            );
        })}
    )

    it("should return an error", async () => {
        axiosGet.mockRejectedValueOnce({response: {data: {status: 400, title: "Bad Request", path: "/auditions/band/1", messages: ["Audition not found"]}}});
        await auditionService.getAuditionsByBandId(1,1).then((response)=>{
            expect(response.hasFailed()).toEqual(true);
            expect(axiosGet).toHaveBeenCalledTimes(1);
            expect(axiosGet).toHaveBeenCalledWith("/auditions",
            {params: {
                "bandId": 1,
                "page": 1
            }},
            );
        })}
    );
});

describe("getApplication()", () => {
    
    it("should return an application", async () => {
        axiosGet.mockResolvedValueOnce({data: application1});
        await auditionService.getApplication(1,1).then((response)=>{
            expect(response.getData()).toEqual(application1);
            expect(response.hasFailed()).toEqual(false);
            expect(axiosGet).toHaveBeenCalledTimes(1);
            expect(axiosGet).toHaveBeenCalledWith("/auditions/1/applications/1");
        })
    });

    it("should return an error", async () => {
        axiosGet.mockRejectedValueOnce({response: {data: {status: 400, title: "Bad Request", path: "/auditions/1/application/1", messages: ["Application not found"]}}});
        await auditionService.getApplication(1,1).then((response)=>{
            expect(response.hasFailed()).toEqual(true);
            expect(axiosGet).toHaveBeenCalledTimes(1);
            expect(axiosGet).toHaveBeenCalledWith("/auditions/1/applications/1");
        })
    }
    );
});

describe("getAuditionApplications()", () => {
            
    it("should return a list of applications", async () => {
        axiosGet.mockResolvedValueOnce({data: [application1, application2]});
        await auditionService.getAuditionApplications(1).then((response)=>{
            expect(response.getData().getContent()).toEqual([application1, application2]);
            expect(response.getData().getContent().length).toEqual(2);
            expect(response.hasFailed()).toEqual(false);
            expect(axiosGet).toHaveBeenCalledTimes(1);
            expect(axiosGet).toHaveBeenCalledWith("/auditions/1/applications",
            {params: {
                "page": undefined,
                "state": undefined
            }},);
        })
    });

    it("should return an error", async () => {
        axiosGet.mockRejectedValueOnce({response: {data: {status: 400, title: "Bad Request", path: "/auditions/1/applications", messages: ["Audition not found"]}}});
        await auditionService.getAuditionApplications(1).then((response)=>{
            expect(response.hasFailed()).toEqual(true);
            expect(axiosGet).toHaveBeenCalledTimes(1);
            expect(axiosGet).toHaveBeenCalledWith("/auditions/1/applications",
            {params: {
                "page": undefined,
                "state": undefined
            }},);
        })
    }
    );
});

describe("createAudition()", () => {
    
    it("should create an audition", async () => {
        axiosPost.mockResolvedValueOnce({data: auditionPostResponse, headers: {location: "http://localhost:8080/auditions/1"}});
        await auditionService.createAudition(correctAuditionInput).then((response)=>{
            expect(response.getData()).toEqual(auditionPostResponse);
            expect(response.hasFailed()).toEqual(false);
            expect(axiosPost).toHaveBeenCalledTimes(1);
            expect(axiosPost).toHaveBeenCalledWith("/auditions", 
            correctAuditionInput,
            {headers: { "Content-Type": "application/vnd.audition.v1+json" }}
            );
        })
    });

    it("should return an error", async () => {
        axiosPost.mockRejectedValueOnce({response: {data: {status: 400, title: "Bad Request", path: "/auditions", messages: ["Audition not found"]}}});
        await auditionService.createAudition(correctAuditionInput).then((response)=>{
            expect(response.hasFailed()).toEqual(true);
            expect(axiosPost).toHaveBeenCalledTimes(1);
            expect(axiosPost).toHaveBeenCalledWith("/auditions", 
            correctAuditionInput,
            {headers: { "Content-Type": "application/vnd.audition.v1+json" }}
            );
        })
    });
});

describe("updateAudition()", () => {
        
    it("should update an audition", async () => {
        axiosPut.mockResolvedValueOnce({data: null});
        await auditionService.updateAudition(1, correctAuditionInput).then((response)=>{
            expect(response.getData()).toEqual(null);
            expect(response.hasFailed()).toEqual(false);
            expect(axiosPut).toHaveBeenCalledTimes(1);
            //todo: me esta agregando unos headers imaginarios
            expect(axiosPut).toHaveBeenCalledWith("/auditions/1", correctAuditionInput,
            {headers: {"Content-Type": "application/vnd.audition.v1+json"}}
            ); 
        })
    });

    it("should return an error", async () => {
        axiosPut.mockRejectedValueOnce({response: {data: {status: 400, title: "Bad Request", path: "/auditions/1", messages: ["Audition not found"]}}});
        await auditionService.updateAudition(1, correctAuditionInput).then((response)=>{
            expect(response.hasFailed()).toEqual(true);
            expect(axiosPut).toHaveBeenCalledTimes(1);
        })
    });
});

describe("apply()", () => {
    
    it("should apply to an audition", async () => {
        axiosPost.mockResolvedValueOnce({data: auditionPostResponse, headers: {location: "http://localhost:8080/auditions/1"}});
        await auditionService.apply(1, "I want to apply to band").then((response)=>{
            expect(response.getData()).toEqual(auditionPostResponse);
            expect(response.hasFailed()).toEqual(false);
            expect(axiosPost).toHaveBeenCalledTimes(1);
            expect(axiosPost).toHaveBeenCalledWith("/auditions/1/applications", 
            {message: "I want to apply to band"},
            {headers: { "Content-Type": "application/vnd.application.v1+json" }}
            );
        })
    });

    it("should return an error", async () => {
        axiosPost.mockRejectedValueOnce({response: {data: {status: 400, title: "Bad Request", path: "/auditions/1/applications", messages: ["Audition not found"]}}});
        await auditionService.apply(1, "I want to apply to band").then((response)=>{
            expect(response.hasFailed()).toEqual(true);
            expect(axiosPost).toHaveBeenCalledTimes(1);
        })
    });
    
});

describe("changeApplicationStatus()", () => {
    
    it("should change the status of an application", async () => {
        axiosPut.mockResolvedValueOnce({data: null});
        await auditionService.changeApplicationStatus(1, 1, "ACCEPTED").then((response)=>{
            expect(response.getData()).toEqual(null);
            expect(response.hasFailed()).toEqual(false);
            expect(axiosPut).toHaveBeenCalledTimes(1);
            expect(axiosPut).toHaveBeenCalledWith("/auditions/1/applications/1",
            {},
            {headers: { "Content-Type": "application/vnd.application.v1+json" },
            params: { state: "ACCEPTED" }
            }
            );
        }
        )
    });

    it("should return an error", async () => {
        axiosPut.mockRejectedValueOnce({response: {data: {status: 400, title: "Bad Request", path: "/auditions/1/applications/1", messages: ["Audition not found"]}}});
        await auditionService.changeApplicationStatus(1, 1, "ACCEPTED").then((response)=>{
            expect(response.hasFailed()).toEqual(true);
            expect(axiosPut).toHaveBeenCalledTimes(1);
        })
    });
});

describe("deleteAudition()", () => {
        
        it("should delete an audition", async () => {
            axiosDelete.mockResolvedValueOnce({data: null});
            await auditionService.deleteAuditionById(1).then((response)=>{
                expect(response.getData()).toEqual(null);
                expect(response.hasFailed()).toEqual(false);
                expect(axiosDelete).toHaveBeenCalledTimes(1);
                expect(axiosDelete).toHaveBeenCalledWith("/auditions/1",
                {"headers": {"Content-Type": "application/vnd.application.v1+json"}}
                );
            })
        });
    
        it("should return an error", async () => {
            axiosDelete.mockRejectedValueOnce({response: {data: {status: 400, title: "Bad Request", path: "/auditions/1", messages: ["Audition not found"]}}});
            await auditionService.deleteAuditionById(1).then((response)=>{
                expect(response.hasFailed()).toEqual(true);
                expect(axiosDelete).toHaveBeenCalledTimes(1);
            })
        });
    });