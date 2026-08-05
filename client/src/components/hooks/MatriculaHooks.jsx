import React from 'react'

export function MatriculaHooks() {

    const BASE_URL = "http://localhost:8080/matriculas"
    async function listarMatriculas(page, pageSize, statusSolicitacao, statusDisciplina, efetivado) {
        try {
            let url = `${BASE_URL}?pagina=${page}&tamanho=${pageSize}`
            
            if(statusSolicitacao != "nenhum"){ 
                url = url + `&area=${statusSolicitacao}` 
            }
            
            if(statusDisciplina != "nenhum") {
                console.log("dentro do if")
                url = url + `&periodo=${statusDisciplina}`
                console.log(url)
            }
            if(efetivado != "nenhum") {
                url = url + `&quantidade-periodos=${efetivado}`
            }

            console.log(url)
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

    return {listarMatriculas}
}
