import { Navigate, useLocation } from 'react-router-dom'
import AuthContext from "./contexts/AuthContext";
import { useContext } from "react";
// TODO: en caso de que el usuario no estaba logueado, estaria bueno redireccionarlo
// TODO: despues del login al path que habia querido ir antes.
interface Props {
  component: React.ComponentType
  path?: string
  roles: Array<string>
}

export const PrivateRoute: React.FC<Props> = ({ component: RouteComponent, roles }) => {
  const { isAuthenticated, role } = useContext(AuthContext);
  const location = useLocation();

  if (isAuthenticated && roles.includes(role as string)) {
    return <RouteComponent />
  }

  if (isAuthenticated && !roles.includes(role as string)) {
    return <Navigate to="/error?code=403" />
  }

  return <Navigate to='/login' state={{ prev: location.pathname }} />
}