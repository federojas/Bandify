import { useState } from "react";
import React from "react";
import jwtDecode, { JwtPayload } from "jwt-decode";
import api from "../api/api";
import User from "../models/User";
import { loginService } from "../services";

type CustomJwtPayload = JwtPayload & { roles: string; userUrl: string };

export interface AuthContextValue {
  isAuthenticated: boolean;
  logout: () => void;
  login: (username: string, password: string, rememberMe: boolean) => Promise<void>;
  jwt?: string | undefined;
  email?: string | undefined;
  role?: string | undefined;
  userId?: number | undefined;
}

const AuthContext = React.createContext<AuthContextValue>({
  isAuthenticated: false,
  logout: () => {},
  login: async () => {},
});

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
  const jwtInLocalStorage = localStorage.hasOwnProperty("jwt");
  const jwtInSessionStorage = sessionStorage.hasOwnProperty("jwt");
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(
    jwtInLocalStorage || jwtInSessionStorage
  );
  const token = jwtInLocalStorage
    ? (localStorage.getItem("jwt") as string)
    : (sessionStorage.getItem("jwt") as string);
  const [jwt, setJwt] = useState<string | undefined>(token);
  const [email, setEmail] = useState<string | undefined>( () => {
      try {
        return jwtDecode<CustomJwtPayload>(jwt as string).sub as string
      } catch (error) {
        console.log(error);
      }
    }
  );
  const [role, setRole] = useState<string | undefined>( () => {
      try {
        return  jwtDecode<CustomJwtPayload>(jwt as string).roles as string
      } catch(error) {
        console.log(error);
      }
  }
   
  );
  const [userId, setUserId] = useState<number | undefined>( () => {
      try {
        return parseInt(jwtDecode<CustomJwtPayload>(jwt as string)
        .userUrl.split("/")
        .pop() as string)
      } catch(error) {
        console.log(error);
      }
  }
    
  );
  const login = async (username: string, password: string, rememberMe: boolean) => {
    try {
      const res = await loginService.login(username, password, rememberMe);
      if (res && res["x-jwt"]) {
        setJwt(res["x-jwt"] as string);
        setIsAuthenticated(true);
        setEmail(jwtDecode<CustomJwtPayload>(res["x-jwt"] as string).sub);
        setRole(jwtDecode<CustomJwtPayload>(res["x-jwt"] as string).roles);
        const id = jwtDecode<CustomJwtPayload>(res["x-jwt"] as string)
          .userUrl.split("/")
          .pop();
        if (id) setUserId(parseInt(id));
        api.defaults.headers.common["Authorization"] = `Bearer ${jwt}`;
      }
    } catch (error) {
      console.log(error);
    }
  };

  const logout = () => {
    localStorage.removeItem("jwt");
    sessionStorage.removeItem("jwt");
    setIsAuthenticated(false);
    setJwt(undefined);
    setEmail(undefined);
    setRole(undefined);
    setUserId(undefined);
  };

  return (
    <AuthContext.Provider
      value={{ isAuthenticated, logout, login, jwt, email, role, userId }}
    >
      {children}
    </AuthContext.Provider>
  );

};

export default AuthContext;
