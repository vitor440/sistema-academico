import React from 'react'

export function UsuarioHook() {

    async function listarUsuarios(page, pageSize) {
        try {
            const response = await fetch(`http://localhost:8080/usuarios?pagina=${page}&tamanho=${pageSize}`, {
                headers: {
                    "Authorization": `Bearer ${localStorage.getItem("access_token")}`
                },
                credentials: "include"
            })

            const data = await response.json()
            return data

        } catch (error) {
            
        }
    }

    return {listarUsuarios}
}