import AuthContext from "./contexts/AuthContext";
import { useContext } from "react";
import AccessDenied from './pages/ErrorPages/AccessDenied';

interface Props {
    component: React.ComponentType
    path?: string
}



export const AnonymousRoute: React.FC<Props> = ({ component: RouteComponent }) => {

const { isAuthenticated } = useContext(AuthContext);
if (!isAuthenticated) {
    return <RouteComponent />
}

return <AccessDenied />
}