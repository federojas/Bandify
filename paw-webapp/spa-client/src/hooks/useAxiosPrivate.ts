// import { axiosPrivate } from "../api";
import { axiosPrivate } from "../api/api";
import { useEffect } from "react";
import useAuth from "./useAuth";

const useAxiosPrivate = () => {
  const auth = useAuth();

  useEffect(() => {

    const requestIntercept = axiosPrivate.interceptors.request.use(
      config => {
        console.log("🚀 ~ file: useAxiosPrivate.ts:44 ~ useEffect ~ config", config)

        if (!config.headers['Authorization']) {
          config.headers['Authorization'] = `Bearer ${auth?.jwt}`;
        }

        console.log("🚀 ~ file: useAxiosPrivate.ts:45 ~ useEffect ~ config", config)

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

    return () => {
      axiosPrivate.interceptors.request.eject(requestIntercept);
      axiosPrivate.interceptors.response.eject(responseIntercept);
    }
  }, [auth])

  return axiosPrivate;
}

export default useAxiosPrivate;