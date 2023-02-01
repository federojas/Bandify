import AuthContext from "./contexts/AuthContext";
import { useContext } from "react";
import AccessDenied from './pages/ErrorPages/AccessDenied';

interface Props {
    component: React.ComponentType
    path?: string
}



export const AnonymousRoute: React.FC<Props> = ({ component: RouteComponent }) => {

const { isAuthenticated } = useContext(AuthContext);
// TODO: aca no alcanza con revisar si esta autenticado, porque esto
// TODO: solo chequea si tiene tokens (pero pueden estar expirados), ademas solo chequea
// TODO: el jwt, cuando en realidad deberia fijarse por el refresh que es el mas importante

if (true) {
    return <RouteComponent />
}

return <AccessDenied />
}