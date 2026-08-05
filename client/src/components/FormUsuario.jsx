import React, { useEffect, useState } from 'react'
import api from '../../../services/api'
import CustomModal from '../../CustomModal'
import { Box, Button, Grid, IconButton, TextField, Typography } from '@mui/material'
import CustomAlert from '../../CustomAlert'
import ClearIcon from '@mui/icons-material/Clear';

const FormUsuario = ({open, handleClose, usuario, atualizar, obterUsuarios}) => {

  const [username, setUsername] = useState("")
  const [email, setEmail] = useState("")
  const [senha, setSenha] = useState("")
  const [loading, setLoading] = useState(false)
  const [openAlert, setOpenAlert] = useState(false)
  const [openErroAlert, setOpenErroAlert] = useState(false)
  const [mensagem, setMensagem] = useState("")

  async function salvarUsuario() {
    try {
        const body = {username: username, email: email, senha: senha}
        await api.post(`/usuarios`, body)
        setMensagem("Usuário atualizado com sucesso!")
        setOpenAlert(true)
        obterUsuarios()
    } catch (error) {
        setMensagem(error)
        setOpenErroAlert(true)
    }
  }
  
  async function atualizaUsuario() {
    try {
        const body = {username: username, email: email, senha: senha}
        await api.put(`/usuarios/${usuario.id}`, body)
        setMensagem("Usuário atualizado com sucesso!")
        setOpenAlert(true)
        if(obterUsuarios) obterUsuarios()
    } catch (error) {
        alert(error)
        setOpenErroAlert(true)
    }
  }

  function limparCampos() {
    setUsername("")
    setEmail("")
    setSenha("")
  }

  useEffect(() => {
    if(atualizar) {
      setUsername(usuario.username)
      setEmail(usuario.email)
      setSenha(usuario.senha)
    }
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
                    limparCampos()
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
                    <TextField label='Username' fullWidth value={username} onChange={(e) => setUsername(e.target.value)}/>
                  </Grid>
                </Grid>
                <Grid container direction='row'>
                  <Grid size={12}>
                    <TextField label='Email' fullWidth value={email} onChange={(e) => setEmail(e.target.value)}/>
                  </Grid>
                </Grid>
                <Grid container direction='row'>
                  <Grid size={12}>
                    <TextField label='senha' fullWidth value={senha} onChange={(e) => setSenha(e.target.value)} hidden/>
                  </Grid>
                </Grid>
          
          </Grid>

          <Box sx={{display:"flex", justifyContent:"end", mt:2}}>
            <Button variant='contained' onClick={atualizar ? atualizaUsuario : salvarUsuario}>{atualizar ? 'Atualizar' : 'Salvar'}</Button>
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

export default FormUsuario