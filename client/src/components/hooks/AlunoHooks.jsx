import React from 'react'

export function AlunoHooks() {

    async function listarAlunos(page, pageSize, nome) {
        const BASE_URL = "http://localhost:8080/alunos"

        try {
            let url = `${BASE_URL}?pagina=${page}&tamanho=${pageSize}`

            if(nome) {
                url = url + `&nome=${nome}`
            }

            const response = await fetch(url, {
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

    return {listarAlunos}
}