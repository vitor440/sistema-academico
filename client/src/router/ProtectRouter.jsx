import React from 'react'
import Error from '../Error'
import { Navigate } from 'react-router-dom'

function ProtectRouter({roles, children}) {
    
    
    const userRoles = localStorage.getItem("roles")

    if(roles.includes(userRoles)) {
        return children
    }
    else {
        
        return <Navigate to={"/logout"}/>
    }
}

export default ProtectRouter