import React from 'react'
import { useState, useEffect } from 'react'

export function DocenteHook() {

    async function countDocentes() {
        try {
            const response = await fetch(`http://localhost:8080/docentes/count`, {
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

    async function listar(page, pageSize) {
        try {
            const response = await fetch(`http://localhost:8080/docentes?pagina=${page}&tamanho=${pageSize}`, {
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

    return {countDocentes, listar}
}