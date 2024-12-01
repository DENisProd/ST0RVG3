import { useEffect } from "react";

import httpService from "./services/http.service";

import { Route, Routes } from "react-router-dom";
import WelcomePage from "./pages/WelcomePage";
import SecuredPage from "./pages/SecuredPage";
import PrivateRoute from "./app/helpers/PrivateRoute";
import { useKeycloak } from "@react-keycloak/web";
import MainLayout from "./layouts/MainLayout/MainLayout";

function App() {
    const { keycloak } = useKeycloak();

    useEffect(() => {
        httpService.configure(keycloak);
    }, []);

    return (
        <MainLayout>
            <Routes>
                <Route path="/" element={<WelcomePage />} />
                <Route
                    path="/secured"
                    element={
                        <PrivateRoute>
                            <SecuredPage />
                        </PrivateRoute>
                    }
                />
            </Routes>
        </MainLayout>
    );
}

export default App;
