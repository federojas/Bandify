import axios from "axios";


jest.mock("axios");

afterEach(() => {(axios.get as any).mockClear();});
afterEach(() => {(axios.post as any).mockClear();});
afterEach(() => {(axios.put as any).mockClear();});
afterEach(() => {(axios.delete as any).mockClear();});

const axiosGet = (axios.get as any);
const axiosPost = (axios.post as any);
const axiosPut = (axios.put as any);
const axiosDelete = (axios.delete as any);

test ("", () => {});
//todo: loginservice?
