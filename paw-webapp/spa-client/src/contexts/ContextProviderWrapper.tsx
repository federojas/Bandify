import React from 'react';
import { AuthContextProvider } from './AuthContext';
import { UserContextProvider } from './UserContext';
interface Props {
    children: React.ReactNode;
}

const ContextProviderWrapper = (props: Props) => {
    return (
        <AuthContextProvider>
            <UserContextProvider>
                {props.children}
            </UserContextProvider>
        </AuthContextProvider>
    )
}

export default ContextProviderWrapper;
