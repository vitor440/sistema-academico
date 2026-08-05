import { Avatar, Box, CircularProgress, Paper, Typography } from '@mui/material'
import React, { useEffect, useState } from 'react'
import api from '../services/api'
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import EmailIcon from '@mui/icons-material/Email';
import PerfilDocente from './docente/PerfilDocente';
import PerfilAluno from './aluno/PerfilAluno';
import PerfilAdmin from './admin/PerfilAdmin';

const DetalhesPerfil = ({role}) => {
    const [dadosPessoais, setDadosPessoais] = useState(null)
    const [loading, setLoading] = useState(false)

    async function getDadosDocente() {
        setLoading(true)
        try {
            const response = await api.get("/docentes/me")
            const data = response.data
            setDadosPessoais({id: data.id, registroInterno: data.registroInterno, cpf: data.cpf, nome: data.nome, email: data.email, dataNascimento: data.dataNascimento,
                telefone: data.telefone, formacao: data.formacao, salario: data.salario, departamentoId: data.departamentoId, usuarioId: data.usuarioId
            })
        } catch (error) {
            
        }
        setLoading(false)
    }

    async function getDadosAluno() {
        setLoading(true)
        try {
            const response = await api.get("/alunos/me")
            const data = response.data
            setDadosPessoais({id: data.id, matricula: data.matricula, cpf: data.cpf, nome: data.nome, email: data.email, dataNascimento: data.dataNascimento,
                telefone: data.telefone, cursoId: data.cursoId, usuarioId: data.usuarioId
            })
        } catch (error) {
            
        }
        setLoading(false)
    }

    async function getDadosAdmin() {
        
        try {
            const response = await api.get("/usuarios/me")
            const data = response.data
            setDadosPessoais({id: data.id, username: data.username, email: data.email, permissions: data.permissions})
        } catch (error) {
            
        }
        
    }

    useEffect(() => {
        setLoading(true)
        if(role === 'DOCENTE') {
            getDadosDocente()
        }
        else if(role === 'ALUNO') {
            getDadosAluno()
        }
        else {
            getDadosAdmin()
        }
        setLoading(false)
    }, [])

   if(loading) {
    <Box sx={{display: 'flex', justifyContent:'center', alignItems:'center'}}>
        <CircularProgress/>
    </Box>
   } 
  
   if(role === 'DOCENTE') {
    return(
        <PerfilDocente dadosPessoais={dadosPessoais}/>
    )
   } 

   if(role === 'ALUNO') {
    return(
        <PerfilAluno dadosPessoais={dadosPessoais}/>
    )
   }

  return (
    <PerfilAdmin dadosPessoais={dadosPessoais}/>
  )
}

export default DetalhesPerfil