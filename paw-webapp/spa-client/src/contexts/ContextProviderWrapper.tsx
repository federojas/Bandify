import React from 'react';
import { AuthContextProvider } from './AuthContext';
interface Props {
    children: React.ReactNode;
}

const ContextProviderWrapper = (props: Props) => {
    return (
        <AuthContextProvider>
            {props.children}
        </AuthContextProvider>
    )
}

export default ContextProviderWrapper;
