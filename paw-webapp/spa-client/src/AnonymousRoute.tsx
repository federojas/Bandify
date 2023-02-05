import AuthContext from "./contexts/AuthContext";
import { useContext } from "react";
import { Navigate } from "react-router-dom";

interface Props {
    component: React.ComponentType
    path?: string
}



export const AnonymousRoute: React.FC<Props> = ({ component: RouteComponent }) => {

const { isAuthenticated } = useContext(AuthContext);
// TODO: aca no alcanza con revisar si esta autenticado, porque esto
// TODO: solo chequea si tiene tokens (pero pueden estar expirados), ademas solo chequea
// TODO: el jwt, cuando en realidad deberia fijarse por el refresh que es el mas importante

if (!isAuthenticated) {
    return <RouteComponent />
}

return <Navigate to="/error?code=403" />
}