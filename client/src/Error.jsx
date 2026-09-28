import { useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import api from './services/api'

const Error = () => {

    
  const navigate = useNavigate()
  useEffect(() => {

    async function logout() {
        try{
            await api.post("/logout")
            localStorage.clear()
            sessionStorage.clear()
            navigate("/login")
        }
        catch(error) {
        }

        
        
    }

    
    logout()

  }, [])

  return (
    <div>
        <h2></h2>
    </div>
  )
}

export default Error