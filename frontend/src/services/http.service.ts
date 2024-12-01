import axios from "axios";
import Keycloak from "keycloak-js";

export const HttpMethods = {
  GET: 'GET',
  POST: 'POST',
  DELETE: 'DELETE',
};

const _axios = axios.create();

export const configure = (keycloak: Keycloak) => {
  _axios.interceptors.request.use((config) => {
    if (keycloak.authenticated) {
      const cb = () => {
        config.headers.Authorization = `Bearer ${keycloak.token}`;
        return Promise.resolve(config);
      };
      return keycloak.updateToken(30).then(cb).catch(() => {
        keycloak.logout();
        return Promise.reject(config);
      });
    }
  });
};

const instance = () => _axios;

export default {
  instance,
  configure
};