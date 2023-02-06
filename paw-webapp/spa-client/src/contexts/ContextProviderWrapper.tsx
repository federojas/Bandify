import React from "react";
import { AuditionServiceProvider } from "./AuditionService";
import { AuthProvider } from "./AuthContext";
import { GenreServiceProvider } from "./GenreService";
import { LocationServiceProvider } from "./LocationService";
import { MembershipServiceProvider } from "./MembershipService";
import { RoleServiceProvider } from "./RoleService";
import { UserServiceProvider } from "./UserService";
interface Props {
  children: React.ReactNode;
}

const ContextProviderWrapper = (props: Props) => {
  return (
    <AuthProvider>
      <AuditionServiceProvider>
        <UserServiceProvider>
          <GenreServiceProvider>
            <RoleServiceProvider>
              <LocationServiceProvider>
                <MembershipServiceProvider>
                  {props.children}
                </MembershipServiceProvider>
              </LocationServiceProvider>
            </RoleServiceProvider>
          </GenreServiceProvider>
        </UserServiceProvider>
      </AuditionServiceProvider>
    </AuthProvider>
  );
};

export default ContextProviderWrapper;
