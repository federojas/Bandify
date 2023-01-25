import { userApi } from "../../api";
import { UserCreateInput } from "../../api/types/User";

const mockUser = {
  email: "john@doe.com",
  password: "password",
  passwordConfirmation: "password",
  name: "John",
  surname: "Doe",
  band: false,
}

describe('getUserById()', () => {
  it('should return a user', async () => {
    const user = await userApi.getUserById(1);
    console.log(user)
  })
})

describe('createNewUser()', () => {
  it('should create a new user', async () => {
    const user = await userApi.createNewUser(mockUser);
    console.log(user)
  })
})

describe('getUsers()', () => {
  it('should return a list of users without using params', async () => {
    const users = await userApi.getUsers();
    console.log(users)
  })
})