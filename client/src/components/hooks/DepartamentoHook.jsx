import React from 'react'
import { useState, useEffect } from 'react'

export function DepartamentoHook() {

    const BASE_URL = "http://localhost:8080/departamentos"
    async function countDepartamentos() {
        try {
            const response = await fetch(`${BASE_URL}/count`, {
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

    async function listar(page, pageSize, nome) {


        try {
            let url = `${BASE_URL}?pagina=${page}&tamanho=${pageSize}`
            
            if(nome != ""){ 
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

    async function obterDepartamentoPeloId(id) {
        try {
            const response = await fetch(`${BASE_URL}/${id}`, {
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

    return {countDepartamentos, listar, obterDepartamentoPeloId}
}