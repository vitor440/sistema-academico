import React from 'react'
import { useEffect } from 'react'
import Cookies from "js-cookie"
import { useNavigate } from 'react-router-dom'
import api from './services/api'

const Error = () => {

    
  const navigate = useNavigate()
  useEffect(() => {

    async function logout() {
        try{
            // await fetch("http://localhost:8080/logout", {
            //     credentials: "include",
            //     method: "GET"
            // })
            await api.post("/logout")
            console.log("logout foi realizado!")
        }
        catch(error) {
        }

        localStorage.clear()
        sessionStorage.clear()
        navigate("/login")
        
    }

    
    logout()

  }, [])

  return (
    <div>
        <h2>Logout</h2>
    </div>
  )
}

export default Error