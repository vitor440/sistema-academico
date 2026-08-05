import dayjs from 'dayjs'
import React, { useEffect, useState } from 'react'


import { Box, Button, Grid, MenuItem, TextField, Typography, IconButton } from '@mui/material'
import ClearIcon from '@mui/icons-material/Clear';

import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { DateField } from '@mui/x-date-pickers/DateField';
import api from '../services/api';
import CustomModal from './CustomModal';
import CustomAlert from './CustomAlert';

const FormDocentes = ({open, handleClose, docente, atualizar, obterDocentes}) => {

  const [nome, setNome] = useState("")
  const [cpf, setCpf] = useState("")
  const [email, setEmail] = useState("")
  const [telefone, setTelefone] = useState("")
  const [senha, setSenha] = useState("")
  const [dataNascimento, setDataNascimento] = useState(dayjs(""))
  const [salario, setSalario] = useState("")
  const [formacao, setFormacao] = useState("") 
  const [departamentoId, setDepartamentoId] = useState("")
  const [departamentos, setDepartamentos] = useState([])
  const [loading, setLoading] = useState(false)
  const [openAlert, setOpenAlert] = useState(false)
  const [openErroAlert, setOpenErroAlert] = useState(false)
  const [mensagem, setMensagem] = useState("")

  async function getDepartamenos(params) {
    try {
        const response = await api.get("/departamentos", {
            params: {
                tamanho: 100
            }
        })

        const data = response.data.content
        setDepartamentos(data)
    } catch (error) {
        if(error.response.status === 401 || error.response.status === 403) {
            setMensagem("Erro de autorização")
            setOpenErroAlert(true)
        }
    }
  }

  async function salvarDocente() {
    try {
        const body = {cpf: cpf, nome: nome, email: email, senha: senha, telefone: telefone, dataNascimento: dataNascimento.format("YYYY-MM-DD"), salario: salario, formacao: formacao, departamentoId: departamentoId}
        await api.post("/docentes", body)
        setMensagem("Docente salvo com sucesso!")
        setOpenAlert(true)
        obterDocentes()
        handleClose()
    } catch (error) {
        
    }
  }

  async function atualizarDocente() {
    try {
        const body = {cpf: cpf, nome: nome, email: email, telefone: telefone, dataNascimento: dataNascimento.format("YYYY-MM-DD"), salario: salario, formacao: formacao, departamentoId: departamentoId}
        await api.put(`/docentes/${docente.id}`, body)
        setMensagem("Docente atualizado com sucesso!")
        setOpenAlert(true)
        obterDocentes()
        handleClose()
    } catch (error) {
        
    }
  }

  function limparCampos() {
        setCpf("")
        setNome("")
        setEmail("")
        setSenha("")
        setDataNascimento(dayjs(""))
        setSalario("")
        setTelefone("")
        setFormacao("")
        setDepartamentoId("")
  }

  useEffect(() => {
    if(atualizar) {
        setCpf(docente?.cpf)
        setNome(docente?.nome)
        setEmail(docente?.email)
        setSenha(docente?.senha)
        setDataNascimento(dayjs(docente?.dataNascimento))
        setSalario(docente?.salario)
        setTelefone(docente?.telefone)
        setFormacao(docente?.formacao)
        setDepartamentoId(docente?.departamentoId)
    }
    getDepartamenos()
  }, [open])

  return (
    <>
    <CustomModal open={open} handleClose={() =>{
        handleClose()
        limparCampos()
    }}>
            <Box sx={{display:"flex", justifyContent:"end"}}>
                        
                <IconButton onClick={() => {
                    handleClose()
                    // limparCampos()
                }}>
                  <ClearIcon/>
                </IconButton>
            </Box>
            <Box sx={{display:"flex", justifyContent:"center", mb:3}}>
              <Typography variant='h5'>{atualizar ? 'Atualizar docente' : 'Cadastrar novo docente'}</Typography>
            </Box>

          <Grid container direction='column' spacing={3}>
                <Grid container direction='row' spacing={2}>
                  <Grid size={12}>
                    <TextField label='Cpf' fullWidth value={cpf} onChange={(e) => setCpf(e.target.value)}/>
                  </Grid>
                </Grid>
                <Grid container direction='row'>
                  <Grid size={12}>
                    <TextField label='Nome' fullWidth value={nome} onChange={(e) => setNome(e.target.value)}/>
                  </Grid>
                </Grid>
                <Grid container direction='row'>
                  <Grid size={12}>
                    <TextField label='Email' fullWidth value={email} onChange={(e) => setEmail(e.target.value)} />
                  </Grid>
                </Grid>
                <Grid container direction='row'>
                  <Grid size={12}>
                    <TextField label='Telefone' fullWidth value={telefone} onChange={(e) => setTelefone(e.target.value)} />
                  </Grid>
                </Grid>
                { !atualizar ? <Grid container direction='row'>
                  <Grid size={12}>
                    <TextField label='Senha' fullWidth value={senha} onChange={(e) => setSenha(e.target.value)} />
                  </Grid>
                </Grid> : <div></div>}
                <Grid container direction='row'>
                  <Grid size={6}>
                    <LocalizationProvider dateAdapter={AdapterDayjs}>
                        <DateField label="Data de nascimento" fullWidth value={dataNascimento} onChange={(e) => setDataNascimento(e)} format='DD/MM/YYYY' />
                    </LocalizationProvider>
                  </Grid>
                  
                  <Grid size={6}>
                    <TextField label='salário' fullWidth value={salario} onChange={(e) => setSalario(e.target.value)} />
                  </Grid>
                </Grid>
                <Grid container direction='row'>
                  <Grid size={12}>
                    <TextField label='Formação' fullWidth value={formacao} onChange={(e) => setFormacao(e.target.value)} />
                  </Grid>
                </Grid>
                <Grid container direction='row'>
                  <Grid size={12}>
                    <TextField label='Departamento' fullWidth value={departamentoId} onChange={(e) => setDepartamentoId(e.target.value)} select>
                        {departamentos?.map(d => {
                            return <MenuItem key={d.id} value={d.id}>{d.nome}</MenuItem>
                        })}
                    </TextField>
                  </Grid>
                </Grid>
          
          </Grid>

          <Box sx={{display:"flex", justifyContent:"end", mt:2}}>
            <Button variant='contained' onClick={atualizar ? atualizarDocente : salvarDocente}>{atualizar ? 'Atualizar' : 'Salvar'}</Button>
          </Box>

          
    </CustomModal>

    <CustomAlert 
          severity={"success"} 
          open={openAlert} 
          handleClose={() => setOpenAlert(false)} 
          vertical={"top"} 
          horizontal={"right"} 
          mensagem={mensagem}/>

          <CustomAlert 
          severity={"error"} 
          open={openErroAlert} 
          handleClose={() => setOpenErroAlert(false)} 
          vertical={"top"} 
          horizontal={"right"} 
          mensagem={mensagem}/>
    </>
  )
}

export default FormDocentes