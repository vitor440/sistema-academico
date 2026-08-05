import React, { useEffect, useState } from 'react'
import ClearIcon from '@mui/icons-material/Clear';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import dayjs from 'dayjs';
import api from '../../../services/api';
import { useNavigate } from 'react-router-dom';
import MenuItem from '@mui/material/MenuItem';
import CustomModal from '../../CustomModal';
import CircularProgress from '@mui/material/CircularProgress';
import Box from '@mui/material/Box';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';

import { DateField } from '@mui/x-date-pickers/DateField';
import Button from '@mui/material/Button';
import CustomAlert from '../../CustomAlert';

const FormAluno = ({open, handleClose, aluno, atualizar, obterAlunos}) => {
  const navigate = useNavigate()
  

  const [cpf, setCpf] = useState("")
  const [email, setEmail] = useState("")
  const [nome, setNome] = useState("")
  const [senha, setSenha] = useState("")
  const [telefone, setTelefone] = useState("")
  const [dataNascimento, setDataNascimento] = useState(dayjs(""))
  const [cursoId, setCursoId] = useState("")
  const [cursos, setCursos] = useState([])
  const [mensagem, setMensagem] = useState("")
  const [openAlert, setOpenAlert] = useState(false)
  const [openErroAlert, setOpenErroAlert] = useState(false)
  const [loading, setLoading] = useState(false)

  

  async function getCursos() {
    setLoading(true)
    try {
      const response = await api.get("/cursos", {
        params: {
          tamanho: 100
        }
      })

      const data = response.data.content
      setCursos(data)
    } catch (error) {
      if(error.response.status === 401 || error.response.status === 403) {
        setMensagem("Erro de autorização ou credenciais expiradas!")
        openErroAlert(true)
        navigate("/login")
      }
    }
    setLoading(false)
  }

  async function salvarAluno() {
    setLoading(true)
    try {
      const body = {cpf: cpf, senha: senha, email: email, nome: nome, telefone: telefone, dataNascimento: dataNascimento.format("YYYY-MM-DD"), cursoId: cursoId}
      await api.post("/alunos", body)
      setMensagem("aluno salvo com sucesso!")
      setOpenAlert(true)
      obterAlunos()
    } catch (error) {
      if(error.response.status === 401 || error.response.status === 403) {
        setMensagem("Erro de autorização ou credenciais expiradas!")
        setOpenErroAlert(true)
        navigate("/login")
      }
      else if(error.response.status === 409) {
        setMensagem("cpf duplicado!")
        setOpenErroAlert(true)

      }

      else {
        setMensagem("Erro de validação")
        setOpenErroAlert(true)
      }
      
    }

    setLoading(false)
  }

  async function atualizarAluno() {
    setLoading(true)
    try {
      const body = {cpf: cpf, email: email, nome: nome, telefone: telefone, dataNascimento: dataNascimento, cursoId: cursoId}
      console.log(body)
      await api.put(`/alunos/${aluno.id}`, body)
      setMensagem("aluno atualizado com sucesso!")
      setOpenAlert(true)
      obterAlunos()
    } catch (error) {
      if(error.response.status === 401 || error.response.status === 403) {
        setMensagem("Erro de autorização ou credenciais expiradas!")
        setOpenErroAlert(true)
        navigate("/login")
      }
      else if(error.response.status === 409) {
        setMensagem("cpf duplicado!")
        setOpenErroAlert(true)

      }

      else {
        setMensagem("Erro de validação")
        setOpenErroAlert(true)
      }
      
    }
    setLoading(false)
  }

  function limparCampos() {
    setCpf("")
    setEmail("")
    setNome("")
    setTelefone("")
    setDataNascimento(dayjs(""))
    setCursoId("")
  }

  useEffect(() => {

    if(atualizar) {
      setNome(aluno.nome)
      setCpf(aluno.cpf)
      setEmail(aluno.email)
      setTelefone(aluno.telefone)
      setDataNascimento(dayjs(aluno.dataNascimento))
      setCursoId(aluno.cursoId)
    }

    getCursos()

  }, [])

  if(loading) {
    return <CustomModal open={open} handleClose={() => {
        handleClose()
        limparCampos()
      }}>
        <Box sx={{display:'flex', justifyContent:'center', alignItems:'center'}}>
          <CircularProgress/>
        </Box>
      </CustomModal>
  }

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
              <Typography variant='h5'>{atualizar ? 'Atualizar aluno' : 'Cadastrar novo aluno'}</Typography>
            </Box>

          <Grid container direction='column' spacing={3}>
                <Grid container direction='row' spacing={2}>
                  <Grid size={12}>
                    <TextField label='Nome' fullWidth value={nome} onChange={(e) => setNome(e.target.value)}/>
                  </Grid>
                </Grid>
                <Grid container direction='row'>
                  <Grid size={12}>
                    <TextField label='Email' fullWidth value={email} onChange={(e) => setEmail(e.target.value)}/>
                  </Grid>
                  <Grid size={12}>
                    <TextField label='Cpf' fullWidth value={cpf} onChange={(e) => setCpf(e.target.value)}/>
                  </Grid>
                </Grid>
                {!atualizar ?<Grid container direction='row' spacing={2}>
                  <Grid size={12}>
                    <TextField label='Senha' fullWidth value={senha} onChange={(e) => setSenha(e.target.value)}/>
                  </Grid>
                </Grid> : <div></div>}
                <Grid container direction='row'>
                  <Grid size={6}>
                    <TextField label='Telefone' fullWidth value={telefone} onChange={(e) => setTelefone(e.target.value)}/>
                  </Grid>
                  <Grid size={6}>
                    <LocalizationProvider dateAdapter={AdapterDayjs}>
                                          
                        <DateField label="data" fullWidth value={dataNascimento} onChange={(e) => setDataNascimento(e)} format='DD/MM/YYYY' />
                      
                    </LocalizationProvider>
                  </Grid>
                </Grid>
                <Grid container direction='row'>
                  <Grid size={12}>
                      <TextField label='curso' fullWidth select value={cursoId} onChange={(e) => setCursoId(e.target.value)}>
                          {cursos?.map(c => {
                            return <MenuItem key={c.id} value={c.id}>{c.nome}</MenuItem>
                          })}
                      </TextField>
                  </Grid>
                </Grid>
          </Grid>

          <Box sx={{display:"flex", justifyContent:"end", mt:2}}>
            <Button variant='contained' onClick={atualizar ? atualizarAluno : salvarAluno}>{atualizar ? 'Atualizar' : 'Salvar'}</Button>
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

export default FormAluno