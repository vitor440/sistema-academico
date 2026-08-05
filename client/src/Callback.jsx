import { parseMarker } from '@fullcalendar/core/internal';
import React, { useRef } from 'react'
import { useSearchParams, useNavigate, redirect} from 'react-router-dom'
import { useEffect } from 'react';
import { jwtDecode } from "jwt-decode";
import api from './services/api';

const Callback = () => {

    const [params] = useSearchParams();
    let navigate = useNavigate();
    // const hasFetched = useRef(false);
    

    async function getDocente() {
      try {
        const response = await api.get("/docentes/me")
        const data = response.data
        localStorage.setItem("docenteId", data.id)
      } catch (error) {
      }
    }

    async function getAluno() {
      try {
        const response = await api.get("/alunos/me")
        const data = response.data
        localStorage.setItem("alunoId", data.id)
      } catch (error) {
        
      }
    }

    async function getToken() {
        try {
            const code = params.get("code")
            const code_verifier = sessionStorage.getItem("code_verifier")

            if (!code || !code_verifier) {
                return navigate("/login");
            }

            const body = new URLSearchParams()
            
            body.append("grant_type", "authorization_code")
            body.append("code", code)
            body.append("redirect_uri", "http://localhost:5173/callback")
            body.append("code_verifier", code_verifier)
            body.append("client_id", "react")

            const response = await api.post("/oauth2/token", body, {
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                }
            })
            
            sessionStorage.removeItem("code_verifier")

            const data = response.data
            

            const token = data.access_token
        
            console.log('token obtido: ' + token)

            const tokenDecoded = jwtDecode(token)

            localStorage.setItem("access_token", token)
            localStorage.setItem("username", tokenDecoded.sub)
            localStorage.setItem("email", tokenDecoded.email)
            localStorage.setItem("roles", tokenDecoded.roles)
            
            if(localStorage.getItem("roles").includes("ALUNO")) {
                await getAluno()
                navigate("/alunos")
            }
            else if(localStorage.getItem("roles").includes("ADMIN")) {
                navigate("/admin")
            }
            else {
                await getDocente()
                navigate("/docentes")
            }
            
        } catch (error) {
            alert(error)
            navigate("/login")
        }
    }

    useEffect(() => {
        
        // if (!hasFetched.current) {
        //     hasFetched.current = true;
        //     getToken();
        // }
        getToken()
    }, [])

  return (
    <div>

    </div>
  )
}

export default Callback