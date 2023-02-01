// import { axiosPrivate } from "../api";
import { axiosPrivate } from "../api/api";
import { useEffect } from "react";
import useAuth from "./useAuth";

const useAxiosPrivate = () => {
  const auth = useAuth();
  
  const requestIntercept = axiosPrivate.interceptors.request.use(
    config => {
    
      if (auth?.isAuthenticated && !config.headers['Authorization']) {
        config.headers['Authorization'] = `Bearer ${auth?.jwt}`;
      }

      return config;
    }, (error) => Promise.reject(error)
  );

  const responseIntercept = axiosPrivate.interceptors.response.use(
    response => response,
    async (error) => {
      const prevRequest = error?.config;
      if (error?.response?.status === 401 && !prevRequest?.sent) {
        // Reintentar la request con refreshToken
        // y guardar los tokens nuevos
        prevRequest.sent = true;
        prevRequest.headers['Authorization'] = `Bearer ${auth?.refreshToken}`;
        const response = await axiosPrivate(prevRequest);
        auth?.login(true, response.headers["x-jwt"], response.headers["x-refresh-token"]);
        return response;
      }
      return Promise.reject(error);
    }
  );
  
  return axiosPrivate;
}

export default useAxiosPrivate;