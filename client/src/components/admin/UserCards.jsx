import React, { use } from 'react'
import { useState, useEffect } from 'react'
import "./UserCards.css"
import { Link } from 'react-router-dom'
import {Paper, Box, Typography, Button, Grid, Divider, IconButton} from '@mui/material'
import EditOutlinedIcon from '@mui/icons-material/EditOutlined';
import DeleteOutlineOutlinedIcon from '@mui/icons-material/DeleteOutlineOutlined';
import CustomModal from '../CustomModal'
import FormAluno from './PageAlunos/FormAluno'
import DeleteOptions from '../DeleteOptions'
import api from '../../services/api'
import FormUsuario from './PageUsuarios/FormUsuario'
import FormDocentes from './PageDocentes/FormDocentes'
import { toast } from 'react-toastify'

const UserCards = ({Icone, data, role, cor, obterDados}) => {

    const [dado, setDado] = useState("")
    const [open, setOpen] = useState(false)
    const [deleteOptions, setDeleteOptions] = useState(false)

    if(role === "DOCENTE") {

        async function deletarDocente() {
            try {
                await api.delete(`/docentes/${data.id}`)
                setDeleteOptions(false)
                obterDados()
                toast.success("Item Deletado!")
            } catch(error){}
        }
        return (
            <Grid size={4}>
                <Paper sx={{p:2, heigth:660}} variant='outlined'>
                    <Box sx={{display:'flex', justifyContent:'center', mb:3}}>
                        <Icone fontSize="70px" color={cor} id="user-icon"/>
                    </Box>

                    <Typography variant='h6'  >Registro Interno: {data.registroInterno}</Typography>
                    <Divider sx={{mb:2}}/>
                    <Typography variant='h6' >Cpf: {data.cpf}</Typography>
                    <Divider sx={{mb:2}}/>
                    <Typography variant='h6' >Nome: {data.nome}</Typography>
                    <Divider sx={{mb:2}}/>
                    <Typography variant='h6' >Email: {data.email}</Typography>
                    <Divider sx={{mb:2}}/>
                    <Typography variant='h6' >Data de Nascimento: {data.dataNascimento}</Typography>
                    <Divider sx={{mb:2}}/>
                    <Typography variant='h6' >Telefone: {data.telefone}</Typography>
                    <Divider sx={{mb:2}}/>
                    <Typography variant='h6' >Formação: {data.formacao}</Typography>
                    <Divider sx={{mb:2}}/>
                    <Typography variant='h6' >Id do departamento: {data.departamentoId}</Typography>

                    <Box sx={{display:'flex', justifyContent:'space-between', mt:2}}>
                        <Box sx={{display:'flex', justifyContent:'space-between'}}>
                            <IconButton onClick={() => setOpen(true)}>
                                <EditOutlinedIcon/>
                            </IconButton>
                            <IconButton onClick={() => setDeleteOptions(true)}>
                                <DeleteOutlineOutlinedIcon/>
                            </IconButton>
                        </Box>
                        <Button variant='text'>Ver Detalhes</Button>
                    </Box>
                </Paper>

                <FormDocentes open={open} handleClose={() => setOpen(false)} docente={data} atualizar={true} obterDocentes={obterDados}/>
                <DeleteOptions 
                open={deleteOptions} 
                handleClose={() => setDeleteOptions(false)} 
                deletar={deletarDocente} mensagem={"Docente deletado com sucesso!"} mensagemErro={"Erro ao deletar docente!"}/>
            </Grid>
        )
    }

    if(role === "ALUNO") {

        async function deletarAluno() {
            try {
                await api.delete(`/alunos/${data.id}`)
                setDeleteOptions(false)
                obterDados()
                toast.success("Item Deletado!")
            } catch(error){}
        }

        return (

            <Grid size={4}>
                <Paper sx={{p:2}} variant='outlined'>
                    <Box sx={{display:'flex', justifyContent:'center', mb:3}}>
                        <Icone fontSize="70px" color={cor} id="user-icon"/>
                    </Box>

                    <Typography variant='h6'  >Matricula: {data.matricula}</Typography>
                    <Divider sx={{mb:2}}/>
                    <Typography variant='h6' >Cpf: {data.cpf}</Typography>
                    <Divider sx={{mb:2}}/>
                    <Typography variant='h6' >Nome: {data.nome}</Typography>
                    <Divider sx={{mb:2}}/>
                    <Typography variant='h6' >Email: {data.email}</Typography>
                    <Divider sx={{mb:2}}/>
                    <Typography variant='h6' >Data de Nascimento: {data.dataNascimento}</Typography>
                    <Divider sx={{mb:2}}/>
                    <Typography variant='h6' >Telefone: {data.telefone}</Typography>
                    <Divider sx={{mb:2}}/>
                    <Typography variant='h6' >Id do Curso: {data.cursoId}</Typography>
                    <Divider sx={{mb:2}}/>

                    <Box sx={{display:'flex', justifyContent:'space-between', mt:2}}>
                        <Box sx={{display:'flex', justifyContent:'space-between'}}>
                            <IconButton onClick={() => setOpen(true)}>
                                <EditOutlinedIcon/>
                            </IconButton>
                            <IconButton onClick={() => setDeleteOptions(true)}>
                                <DeleteOutlineOutlinedIcon/>
                            </IconButton>
                        </Box>
                        <Button variant='text'>Ver Detalhes</Button>
                    </Box>
                </Paper>
                <FormAluno open={open} handleClose={() => setOpen(false)} aluno={data} atualizar={true} obterAlunos={obterDados}/>
                <DeleteOptions 
                open={deleteOptions} 
                handleClose={() => setDeleteOptions(false)} 
                deletar={deletarAluno} mensagem={"Aluno deletado com sucesso!"} mensagemErro={"Erro ao deletar aluno!"}/>
            </Grid>
        )
    }

    else {
        const roles = data.permissions.map(p => p.role)

        async function deletarUsuario() {
            try {
                await api.delete(`/usuarios/${data.id}`)
                setDeleteOptions(false)
                obterDados()
                toast.success("Item Deletado!")
            } catch(error){}
        }
        return(

            <Grid size={4}>
                <Paper sx={{p:2}} variant='outlined'>
                    <Box sx={{display:'flex', justifyContent:'center', mb:3}}>
                        <Icone fontSize="70px" color={cor} id="user-icon"/>
                    </Box>

                    <Typography variant='h6'  >username: {data.username}</Typography>
                    <Divider sx={{mb:2}}/>
                    <Typography variant='h6' >Email: {data.email}</Typography>
                    <Divider sx={{mb:2}}/>
                    <Typography variant='h6' >roles: {roles}</Typography>

                    <Box sx={{display:'flex', justifyContent:'space-between', mt:2}}>
                        <Box sx={{display:'flex', justifyContent:'space-between'}}>
                            <IconButton onClick={() => setOpen(true)}>
                                <EditOutlinedIcon/>
                            </IconButton>
                            <IconButton onClick={() => setDeleteOptions(true)}>
                                <DeleteOutlineOutlinedIcon/>
                            </IconButton>
                        </Box>
                        <Button variant='text'>Ver Detalhes</Button>
                    </Box>
                </Paper>
                <DeleteOptions 
                open={deleteOptions} 
                handleClose={() => setDeleteOptions(false)} 
                deletar={deletarUsuario} mensagem={"usuário deletado com sucesso!"} mensagemErro={"Erro ao deletar usuário!"}/>
                <FormUsuario open={open} handleClose={() => setOpen(false)} usuario={data} atualizar={true} obterUsuarios={obterDados}/>
            </Grid>
        )
    }
}

export default UserCards