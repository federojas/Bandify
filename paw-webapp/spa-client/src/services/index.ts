import { LoginService } from "./LoginService";
import { UserService } from "./UserService";

const loginService = new LoginService();
const userService = new UserService();

export { loginService, userService };