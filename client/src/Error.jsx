import { useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import api from './services/api'

const Error = () => {

    
  const navigate = useNavigate()
  useEffect(() => {

    async function logout() {
        try{
            localStorage.clear()
            sessionStorage.clear()
            window.location.replace(`${import.meta.env.VITE_API_URL}/logout`)
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