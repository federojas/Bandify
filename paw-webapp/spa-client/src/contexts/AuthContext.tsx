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
  login: (rememberMe: boolean, token: string) => Promise<void>;
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
  console.log("🚀 ~ file: AuthContext.tsx:28 ~ AuthProvider ~ jwtInLocalStorage", jwtInLocalStorage)
  const jwtInSessionStorage = sessionStorage.hasOwnProperty("jwt");
  console.log("🚀 ~ file: AuthContext.tsx:30 ~ AuthProvider ~ jwtInSessionStorage", jwtInSessionStorage)
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(
    jwtInLocalStorage || jwtInSessionStorage
  );

  

  const token = jwtInLocalStorage
    ? (localStorage.getItem("jwt") as string)
    : (sessionStorage.getItem("jwt") as string);
    const [jwt, setJwt] = useState<string | undefined>(token);
    
    console.log("🚀 ~ file: AuthContext.tsx:39 ~ AuthProvider ~ localStorage.getItem(jwt)", localStorage.getItem("jwt"))
    console.log("🚀 ~ file: AuthContext.tsx:40 ~ AuthProvider ~ sessionStorage", sessionStorage.getItem("jwt"))
  
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
  const login = async (rememberMe: boolean, token: string) => {
    try {
      setJwt(token);
      setIsAuthenticated(true);
      setEmail(jwtDecode<CustomJwtPayload>(token).sub);
      setRole(jwtDecode<CustomJwtPayload>(token as string).roles);
      const id = jwtDecode<CustomJwtPayload>(token)
        .userUrl.split("/")
        .pop();
      if (id) setUserId(parseInt(id));
      api.defaults.headers.common["Authorization"] = `Bearer ${jwt}`;

      if (rememberMe) { 
        console.log("remember me")
        localStorage.setItem("jwt", token);
      }
      sessionStorage.setItem("jwt", token);
      console.log("login success");

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
