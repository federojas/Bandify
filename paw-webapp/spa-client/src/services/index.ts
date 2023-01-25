import { LoginService } from "./LoginService";
import { UserService } from "./UserService";
import { RoleService } from "./RoleService";
import { GenreService } from "./GenreService";

const loginService = new LoginService();
const userService = new UserService();
const roleService = new RoleService();
const genreService = new GenreService();


export { loginService, userService, roleService, genreService };