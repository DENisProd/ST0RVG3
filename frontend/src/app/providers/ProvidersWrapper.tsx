import { ReactKeycloakProvider } from "@react-keycloak/web";
import React from "react";
import keycloak from "../../keycloak";
import { BrowserRouter } from "react-router-dom";
import { App } from "antd";

interface Props {
    children: React.ReactNode;
}

const ProvidersWrapper: React.FC<Props> = ({ children }) => {
    return (
        <App>
            <ReactKeycloakProvider authClient={keycloak}>
                <BrowserRouter>{children}</BrowserRouter>
            </ReactKeycloakProvider>
        </App>
    );
};

export default ProvidersWrapper;
