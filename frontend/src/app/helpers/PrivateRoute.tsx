import { useKeycloak } from "@react-keycloak/web";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

interface Props {
    children: React.ReactNode;
}

const PrivateRoute: React.FC<Props> = ({ children }) => {
    const { keycloak } = useKeycloak();
    const navigate = useNavigate();

    const isLoggedIn = keycloak.authenticated;

    useEffect(() => {
        if (!isLoggedIn) {
            navigate(-1);
        }
    }, [isLoggedIn, navigate]);

    if (!isLoggedIn) {
        return null;
    }

    return <>{children}</>;
};

export default PrivateRoute;
