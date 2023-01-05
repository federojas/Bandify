import React from "react";
import { AuthProvider } from "./AuthContext";
import { UserContextProvider } from "./UserContext";
interface Props {
  children: React.ReactNode;
}

const ContextProviderWrapper = (props: Props) => {
  return (
    <AuthProvider>
      <UserContextProvider>{props.children}</UserContextProvider>
    </AuthProvider>
  );
};

export default ContextProviderWrapper;
