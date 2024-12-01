import httpService from "./http.service";

async function fetchUserByEmail (email: string) {
    return await httpService.instance().get('http://localhost:8083/api/v1/customers/users/' + email)
}
async function fetchUserById (id: number) {
    return await httpService.instance().get('http://localhost:8083/api/v1/customers/users/' + id)
}

export default {
    fetchUserByEmail,
    fetchUserById,
};
