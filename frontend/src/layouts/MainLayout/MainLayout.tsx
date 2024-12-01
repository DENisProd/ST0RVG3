import { useState } from "react";
import Navigation from "../../components/Navigation/Navigation";
import styles from "./MainLayout.module.scss";
import { Layout, MenuTheme, Switch } from "antd";
import FooterComponent from "../../components/Footer/Footer";
import Sidebar from "../../components/Sidebar/Sidebar";

const { Header, Footer, Sider, Content } = Layout;

interface Props {
    children: React.ReactNode;
}

const MainLayout: React.FC<Props> = ({ children }) => {
    const [menuTheme, setMenuTheme] = useState<MenuTheme>("light");

    const changeTheme = (value: boolean) => {
        setMenuTheme(value ? "dark" : "light");
    };

    return (
        <Layout className={styles.layout}>
            <Navigation />
            <Layout>
                <Sidebar />
                <Layout>
                    <Content>{children}</Content>
                    <FooterComponent />
                </Layout>
            </Layout>
        </Layout>
    );
};

export default MainLayout;
