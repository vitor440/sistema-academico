import React from 'react'
import { redirect } from 'react-router-dom'
import { useEffect } from 'react'
import pkceChallenge from "pkce-challenge";

const Login = () => {
  const BASE_URL = "http://localhost:8080"
  useEffect(() => {

    async function redirecionar() { 
      try {
        const challenge = await pkceChallenge()

        const code_verifier = challenge.code_verifier
        const code_challenge = challenge.code_challenge

        sessionStorage.setItem("code_verifier", code_verifier)
        // window.location.replace("http://localhost:8080/oauth2/authorize?response_type=code&client_id=react&redirect_uri=http%3A%2F%2Flocalhost%3A5173%2Fcallback")
        
        const url = `${BASE_URL}/oauth2/authorize?` +
                    "response_type=code" +
                    "&client_id=react" +
                    "&redirect_uri=http%3A%2F%2Flocalhost%3A5173%2Fcallback" +
                    `&code_challenge=${code_challenge}` +
                    `&code_challenge_method=S256`

        
        window.location.replace(url)
      } catch(error) {}

    }

    redirecionar()
  }, [])

  return (
    <div>
        
    </div>
  )
}

export default Login