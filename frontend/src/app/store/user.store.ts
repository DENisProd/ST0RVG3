import { create } from "zustand";
import userService from "../../services/user.service";

export interface User {
    id: number;
    firstName: string;
}

interface UserState {
    user: User | null,

    fetchUserById: (id: number) => void,
    fetchUserByEmail: (email: string) => void,
    setUser: (user: User) => void
}

export const useUserStore = create<UserState>((set, get) => ({
    user: null,

    fetchUserById: (id: number) => {
        setTimeout(() => {
            userService.fetchUserById(id).then(() => {

            });
            set({ user: { id, firstName: "John Doe" } });
        }, 1000);
    },
    fetchUserByEmail: (email: string) => {
        setTimeout(() => {
            userService.fetchUserByEmail(email).then((res) => {
                console.log(res);
            });
            // set({ user: { id, firstName: "John Doe" } });
        }, 1000);
    },
    setUser: (user: User) => {
        set({
            user
        })
    }
}))