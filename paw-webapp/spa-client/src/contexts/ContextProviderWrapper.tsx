import React from "react";
import { AuditionServiceProvider } from "./AuditionService";
import { AuthProvider } from "./AuthContext";
interface Props {
  children: React.ReactNode;
}

const ContextProviderWrapper = (props: Props) => {
  return (
    <AuthProvider>
      <AuditionServiceProvider>
        {props.children}
      </AuditionServiceProvider>
    </AuthProvider>
  );
};

export default ContextProviderWrapper;
