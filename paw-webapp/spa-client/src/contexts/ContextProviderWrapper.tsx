import React from "react";
import { AuthProvider } from "./AuthContext";
import { UserContextProvider } from "./UserContext";
interface Props {
  children: React.ReactNode;
}

const ContextProviderWrapper = (props: Props) => {
  return (
    <AuthProvider>
      {props.children}
    </AuthProvider>
  );
};

export default ContextProviderWrapper;
