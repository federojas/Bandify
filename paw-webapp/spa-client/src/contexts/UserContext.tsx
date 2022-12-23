import React from 'react';

interface UserContextType {
    user: null;
}

const UserContext = React.createContext<UserContextType>(null!);

export const UserContextProvider = ({children} : {children: React.ReactNode}) => {
    const [user, setUser] = React.useState(null);

    return (
        <UserContext.Provider value={{user}}>
            {children}
        </UserContext.Provider>
    );
}

export default UserContext;