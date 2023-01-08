import React from "react";
import { User } from "../api/types/User";
import AuthContext from "./AuthContext";
import { useEffect } from "react";
import { userService } from "../services";
interface UserContextType {
  user?: User;
}

const UserContext = React.createContext<UserContextType>({});

export const UserContextProvider = ({
  children,
}: {
  children: React.ReactNode;
}) => {
  const [user, setUser] = React.useState<User | undefined>(undefined);
  const authContext = React.useContext(AuthContext);

  async function getUser() {
    try {
      if (authContext.userId) {
        const data = await userService.getUserById(authContext.userId);
        if (data) setUser(data);
      }
    } catch (error) {
      console.log(error);
    }
  }

  if (authContext.isAuthenticated) {
    console.log("Por llamar al usuario");
    getUser();
  }

  return (
    <UserContext.Provider value={{ user }}>{children}</UserContext.Provider>
  );
};

export default UserContext;
