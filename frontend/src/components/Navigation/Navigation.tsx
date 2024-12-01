import { useKeycloak } from "@react-keycloak/web";
import { Link } from "react-router-dom";
import { Button, Flex, Layout, Typography } from "antd";

const { Header } = Layout;

const Navigation = () => {
    const { keycloak, initialized } = useKeycloak();

    return (
        <Header
            style={{
                width: "100%",
                backgroundColor: "var(--primary-color)",
            }}
        >
            <Flex gap="small" align="center" justify="space-between">
                <Typography>
                    <b>DARKSECRETS</b>
                </Typography>
                <Flex gap="small" align="center">
                    <Link to={""}>Home</Link>
                    <Link to={"secured"}>Secured</Link>

                    {!keycloak.authenticated && (
                        <Button type="primary" onClick={() => keycloak.login()}>
                            Login
                        </Button>
                    )}

                    {!!keycloak.authenticated && (
                        <Button type="default" onClick={() => keycloak.logout()}>
                            Logout ({keycloak.tokenParsed && keycloak.tokenParsed.preferred_username})
                        </Button>
                    )}
                </Flex>
            </Flex>
        </Header>
    );
};

export default Navigation;
