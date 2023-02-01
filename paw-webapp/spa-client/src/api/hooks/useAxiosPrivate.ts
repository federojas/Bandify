import { axiosPrivate } from "../api";
import { useEffect } from "react";
import useAuth from "./useAuth";
import React from "react";
import AuthContext from "../../contexts/AuthContext";

const useAxiosPrivate = () => {
    const auth = React.useContext(AuthContext);

    useEffect(() => {

        const requestIntercept = axiosPrivate.interceptors.request.use(
            config => {
                if (!config.headers['Authorization']) {
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
                    console.log("La request fallo con un 401, ahora voy a usar refresh");
                    console.log(auth?.refreshToken);
                    prevRequest.sent = true;
                    prevRequest.headers['Authorization'] = `Bearer ${auth?.refreshToken}`;
                    const response = await axiosPrivate(prevRequest);
                    auth?.login(true, response.headers["x-jwt"], response.headers["x-refresh-token"]);
                    console.log("mi nuevo refresh:", auth?.refreshToken);
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