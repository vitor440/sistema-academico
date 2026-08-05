import axios from "axios"
import { toast } from "react-toastify"

const api = axios.create({
    baseURL: "http://localhost:8080",
    withCredentials: true
    // headers: {
    //     Authorization: `Bearer ${localStorage.getItem("access_token")}`
    // }
})



api.interceptors.request.use((config) => {
    const token = localStorage.getItem("access_token")

    // Verifica se é a rota de OAuth2 usando flexibilidade no final da URL
    const isAuthRoute = config.url?.endsWith("/oauth2/token");

    if (token && !isAuthRoute) {
        config.headers.Authorization = `Bearer ${token}`

    }
    return config
},
    (error) => {
        console.log("erro ao inserir token na requisição.")
        return Promise.reject(error)
    }
)

api.interceptors.response.use((response) => response,
    (error) => {
        if (error.response && error.response.data) {
            if (error.response.status === 401) {
                localStorage.removeItem("access_token")
                toast.error("Credenciais expiradas. faça login novamente!")
                window.location.href = '/logout'
            }
            else if (error.response.status === 403) {
                localStorage.removeItem("access_token")
                toast.error("Acesso negado!")
                window.location.href = '/logout'
            }
            else if (error.response.status === 409) {
                const { erro } = error.response.data
                toast.error(erro)
            }

            else if (error.response.status === 422) {
                const { campos } = error.response.data
                campos?.forEach(e => toast.error(e.campo + ": " + e.erro))
            }

            

            else {
                toast.error("Requisição inválida. Verifique os dados enviados.")
            }


        }
        else {
            localStorage.removeItem("access_token")
            toast.error("Erro no servidor")
            window.location.href = '/logout'
        
        }

        return Promise.reject(error)
    })

export default api