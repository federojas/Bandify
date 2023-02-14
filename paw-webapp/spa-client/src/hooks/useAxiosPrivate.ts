import axios from "axios";
import { useEffect } from "react";
import { axiosPrivate } from "../api/api";
import useAuth from "./useAuth";

const useAxiosPrivate = () => {
  const auth = useAuth();

  useEffect( () => {
    const requestIntercept = axiosPrivate.interceptors.request.use(
      config => {
        if (auth.isAuthenticated && config.headers && !(config.headers as any)['Authorization']) {
          (config.headers as any)['Authorization'] = `Bearer ${auth.jwt}`;
        } else if( !auth.isAuthenticated && config.headers && (config.headers as any)['Authorization']) {
          (config.headers as any)['Authorization'] = null;
        }
        return config;
      }, (error) => Promise.reject(error)
    );
    
    const responseIntercept = axiosPrivate.interceptors.response.use(
      response => response,
      async (error) => {
        const prevRequest = error?.config;
        if (error?.response?.status === 401 && !prevRequest?.sent) {
          prevRequest.sent = true;
          prevRequest.headers['Authorization'] = `Bearer ${auth.refreshToken}`;
    
          const response = axiosPrivate(prevRequest).then((response) => {
            if (response.headers && response.headers["x-jwt"]) {
              auth.login(response.headers["x-jwt"]);
            }
            return response;
          }).catch((error) => {
            if (error?.response?.status === 401) {
              auth.logout();
            }
            throw error;
          });
          return response;
        }
        return Promise.reject(error);
      }
    );

    return () => {
      axiosPrivate.interceptors.request.eject(requestIntercept);
      axiosPrivate.interceptors.response.eject(responseIntercept);
    }
  }, [auth.isAuthenticated])
  return axiosPrivate;
}

export default useAxiosPrivate;