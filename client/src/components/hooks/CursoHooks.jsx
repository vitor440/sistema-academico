import React from 'react'
import { useState, useEffect } from 'react'

export function CursoHooks() {

    const BASE_URL = "http://localhost:8080/cursos"
    async function listar(page, pageSize, nome, area, turno, periodos) {
        console.log("area: " + area)
        console.log("turno: " + turno)
        console.log("periodos: " + periodos)
        console.log("Nome: " + nome)

        try {
        let url = `${BASE_URL}?pagina=${page}&tamanho=${pageSize}`
            
            if(nome && nome != ""){ 
                console.log("dentro do if nome")
                url = url + `&nome=${nome}` 
            }
            
            
            if(area && area != "nenhum"){ 
                url = url + `&area=${area}` 
            }
            
            if(turno && turno != "nenhum" ) {
                url = url + `&periodo=${turno}`
            }

            if(periodos && periodos != "nenhum") {
                console.log(periodos)
                url = url + `&quantidade-periodos=${periodos}`
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
            alert(error)
        }
    }

    async function cursosPorAlunos() {

        try {
        const response = await fetch(`http://localhost:8080/cursos/alunos-curso`, {
            headers: {
                "Authorization": `Bearer ${localStorage.getItem("access_token")}`
            },
            credentials: "include"
        })
        const data = await response.json()
    
        return data
            
        } catch (error) {
            alert(error)
        }
    }
    
    async function areasPorCurso() {
        try {
        const response = await fetch(`http://localhost:8080/cursos/areas-count`, {
            headers: {
                "Authorization": `Bearer ${localStorage.getItem("access_token")}`
            },
            credentials: "include"
        })
        const data = await response.json()
    
        return data
            
        } catch (error) {
            alert(error)
        }
    }

    async function countCursos() {
        try {
            const response = await fetch(`http://localhost:8080/cursos/count`, {
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
    

    return {listar, cursosPorAlunos, areasPorCurso, countCursos};
}