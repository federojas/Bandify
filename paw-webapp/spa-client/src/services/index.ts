import { LoginService } from "./LoginService";
import { UserService } from "./UserService";
import { RoleService } from "./RoleService";
import { GenreService } from "./GenreService";
import { LocationService } from "./LocationService";

const loginService = new LoginService();
const userService = new UserService();
const roleService = new RoleService();
const genreService = new GenreService();
const locationService = new LocationService();


export { loginService, userService, roleService, genreService, locationService };