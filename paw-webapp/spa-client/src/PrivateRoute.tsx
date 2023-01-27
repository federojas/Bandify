import { Navigate } from 'react-router-dom'
import AuthContext from "./contexts/AuthContext";
import { useContext } from "react";
import AccessDenied from './pages/ErrorPages/AccessDenied';
// TODO: en caso de que el usuario no estaba logueado, estaria bueno redireccionarlo
// TODO: despues del login al path que habia querido ir antes.
interface Props {
    component: React.ComponentType
    path?: string
    roles: Array<string>
}

export const PrivateRoute: React.FC<Props> = ({ component: RouteComponent, roles }) => {

const { isAuthenticated, role } = useContext(AuthContext);
if (isAuthenticated && roles.includes(role as string)) {
    // TODO: reemplazar por navigate
    return <RouteComponent />
}

if (isAuthenticated && !roles.includes(role as string)) {
    // TODO: reemplazar por navigate
    return <AccessDenied />
}

return <Navigate to="/login" />
}