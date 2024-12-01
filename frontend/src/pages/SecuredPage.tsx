import { useKeycloak } from "@react-keycloak/web";
import React from "react";
import { useUserStore } from "../app/store/user.store";
import { Flex, Layout } from "antd";

const { Header, Footer, Sider, Content } = Layout;

const SecuredPage = () => {
    const { keycloak } = useKeycloak();
    const { fetchUserByEmail } = useUserStore();

    const getData = () => {
        const userId = keycloak?.tokenParsed?.sub ? +keycloak.tokenParsed.sub : null;
        const userEmail = keycloak?.tokenParsed?.email;
        if (userEmail) {
            fetchUserByEmail(userEmail);
        }
    };

    return (
        <div>
            <button onClick={getData}>Получить данные</button>
            SecuredPage
        </div>
    );
};

export default SecuredPage;
