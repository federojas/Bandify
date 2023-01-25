import RoleApi from './RoleApi'
import UserApi from './UserApi'
import GenreApi from "./GenreApi";
import LocationApi from "./LocationApi";
import AuditionApi from "./AuditionApi";

const roleApi = new RoleApi();
const userApi = new UserApi();
const genreApi = new GenreApi();
const locationApi = new LocationApi();
const auditionApi = new AuditionApi();

export {
  roleApi,
  userApi,
  genreApi,
  locationApi,
  auditionApi
}
