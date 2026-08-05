import React, { useEffect, useState } from 'react'
import api from '../../../services/api'
import CustomModal from '../../CustomModal'
import { Box, Button, CircularProgress, Grid, IconButton, TextField, Typography } from '@mui/material'
import CustomAlert from '../../CustomAlert'
import ClearIcon from '@mui/icons-material/Clear';
import { toast } from 'react-toastify'

const FormUsuario = ({ open, handleClose, usuario, atualizar, obterUsuarios }) => {

  const [username, setUsername] = useState("")
  const [email, setEmail] = useState("")
  const [senha, setSenha] = useState("")
  const [loading, setLoading] = useState(false)

  async function salvarUsuario() {
    setLoading(true)
    try {
      const body = { username: username, email: email, senha: senha }
      await api.post(`/usuarios`, body)
      toast.success("Usuário salvo com sucesso!")
      obterUsuarios()
      handleClose()
    } catch (error) {
    }
    setLoading(false)
  }

  async function atualizaUsuario() {
    setLoading(true)
    try {
      console.log(usuario)
      const body = { username: username, email: email }
      await api.put(`/usuarios/${usuario?.id}`, body)
      toast.success("Usuário salvo com sucesso!")
      handleClose()
      if (obterUsuarios) obterUsuarios()

    } catch (error) {
    }
    setLoading(false)
  }

  function limparCampos() {
    setUsername("")
    setEmail("")
    setSenha("")
  }

  useEffect(() => {
    if (atualizar) {
      setUsername(usuario?.username)
      setEmail(usuario?.email)
      setSenha(usuario?.senha)
    }
  }, [open])

  if (loading) {
    return <CustomModal open={open} handleClose={() => {
      handleClose()
      limparCampos()
    }}>
      <Box sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
        <CircularProgress />
      </Box>
    </CustomModal>
  }

  return (
    <>
      <CustomModal open={open} handleClose={() => {
        handleClose()
        limparCampos()
      }}>
        <Box sx={{ display: "flex", justifyContent: "end" }}>

          <IconButton onClick={() => {
            handleClose()
            limparCampos()
          }}>
            <ClearIcon />
          </IconButton>
        </Box>
        <Box sx={{ display: "flex", justifyContent: "center", mb: 3 }}>
          <Typography variant='h5'>{atualizar ? 'Atualizar usuário admin' : 'Cadastrar novo usuário admin'}</Typography>
        </Box>

        <Grid container direction='column' spacing={3}>
          <Grid container direction='row' spacing={2}>
            <Grid size={12}>
              <TextField label='Username' fullWidth value={username} onChange={(e) => setUsername(e.target.value)} />
            </Grid>
          </Grid>
          <Grid container direction='row'>
            <Grid size={12}>
              <TextField label='Email' fullWidth value={email} onChange={(e) => setEmail(e.target.value)} />
            </Grid>
          </Grid>
          {!atualizar ? <Grid container direction='row'>
            <Grid size={12}>
              <TextField label='senha' fullWidth value={senha} onChange={(e) => setSenha(e.target.value)} />
            </Grid>
          </Grid> : <Grid></Grid>}

        </Grid>

        <Box sx={{ display: "flex", justifyContent: "end", mt: 2 }}>
          <Button variant='contained' onClick={atualizar ? atualizaUsuario : salvarUsuario}>{atualizar ? 'Atualizar' : 'Salvar'}</Button>
        </Box>


      </CustomModal>
    </>
  )
}

export default FormUsuario