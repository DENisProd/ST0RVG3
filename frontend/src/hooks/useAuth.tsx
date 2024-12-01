import React, { useEffect, useRef, useState } from "react";
import userService from "../services/user.service";
import { useUserStore } from "../app/store/user.store";

const useAuth = () => {
    const { setUser } = useUserStore();
    const isRun = useRef(false);
    const [isLogin, setIsLogin] = useState<boolean>(false);

    const checkAuth = () => {
        const _isLoggedId = userService.isLoggedIn()
        setIsLogin(_isLoggedId);
        console.log(userService.getParsedToken())
        // setUser({})
    }

    useEffect(() => {
        if (!isRun.current) {
            isRun.current = true;
            // userService.initKeycloak(() => {
            //     checkAuth();
            // });
        }

        checkAuth();
    }, []);

    return isLogin;
};

export default useAuth;
